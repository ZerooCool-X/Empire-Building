package engine;

import java.util.ArrayList;

import buildings.*;
import exceptions.*;
import units.*;

public class Player {

	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury;
	private double food;

	public Player(String name) {
		this.name = name;
		controlledCities = new ArrayList<>();
		controlledArmies = new ArrayList<>();
		treasury = 5000;
		food = 0;
	}

	public void add(City c) {
		controlledCities.add(c);
	}

	public int getTreasury() {
		return (int) Math.round(treasury);
	}

	public void setTreasury(double treasury) {
		this.treasury = treasury;
	}

	public int getFood() {
		return (int) Math.round(food);
	}

	public void setFood(double food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public ArrayList<City> getControlledCities() {
		return controlledCities;
	}

	public ArrayList<Army> getControlledArmies() {
		return controlledArmies;
	}

//------------------------------------------------------
	public void recruitUnit(String type, String cityName)
			throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException {

		City city = null;
		for (int i = 0; i < controlledCities.size(); i++) {

			if (controlledCities.get(i).getName().equals(cityName)) {
				city = controlledCities.get(i);
				break;
			}
		}
		if (city != null) {
			MilitaryBuilding building = null;
			ArrayList<MilitaryBuilding> buildingsList = city.getMilitaryBuildings();
			if (type.equals("Archer")) {
				for (int i = 0; i < buildingsList.size(); i++) {
					if (buildingsList.get(i) instanceof ArcheryRange) {
						building = buildingsList.get(i);

						break;
					}
				}
			} else if (type.equals("Infantry")) {
				for (int i = 0; i < buildingsList.size(); i++) {
					if (buildingsList.get(i) instanceof Barracks) {
						building = buildingsList.get(i);
						break;
					}
				}
			} else {
				for (int i = 0; i < buildingsList.size(); i++) {
					if (buildingsList.get(i) instanceof Stable) {
						building = buildingsList.get(i);
						break;
					}
				}
			}

			if (building != null) {
				if (building.isCoolDown()) {
					throw new BuildingInCoolDownException();
				}
				if (treasury < building.getRecruitmentCost()) {
					throw new NotEnoughGoldException();
				}
				if (building.getCurrentRecruit() >= building.getMaxRecruit()) {
					throw new MaxRecruitedException();
				}
				treasury -= building.getRecruitmentCost();
				Unit unit = building.recruit();
				unit.setParentArmy(city.getDefendingArmy());
				city.getDefendingArmy().add(unit);

			}
		}
	}

	public void build(String type, String cityName) throws NotEnoughGoldException {
		City city = null;
		for (int i = 0; i < controlledCities.size(); i++) {

			if (controlledCities.get(i).getName().equals(cityName)) {
				city = controlledCities.get(i);
				break;
			}
		}

		Building building = null;
		boolean isMilitary = true;
		switch (type) {
		case "Stable":
			building = new Stable();
			break;
		case "ArcheryRange":
			building = new ArcheryRange();
			break;
		case "Barracks":
			building = new Barracks();
			break;
		case "Farm":
			building = new Farm();
			isMilitary = false;
			break;
		case "Market":
			building = new Market();
			isMilitary = false;
			break;
		}

		if (treasury < building.getCost()) {
			throw new NotEnoughGoldException();
		} else {
			boolean existing = false;
//			System.out.println(city);
			if (isMilitary) {
				ArrayList<MilitaryBuilding> militaryBuildings = city.getMilitaryBuildings();
				for (int i = 0; i < militaryBuildings.size(); i++) {
					if (militaryBuildings.get(i) instanceof ArcheryRange && type.equals("ArcheryRange")) {
						existing = true;
					} else if (militaryBuildings.get(i) instanceof Barracks && type.equals("Barracks")) {
						existing = true;
					} else if (militaryBuildings.get(i) instanceof Stable && type.equals("Stable")) {
						existing = true;
					}
				}
				if (!existing) {
					city.getMilitaryBuildings().add((MilitaryBuilding) building);
					treasury -= building.getCost();
				}
			} else {
				ArrayList<EconomicBuilding> economicalBuildings = city.getEconomicalBuildings();
				for (int i = 0; i < economicalBuildings.size(); i++) {
					if (economicalBuildings.get(i) instanceof Farm && type.equals("Farm")) {
						existing = true;
					} else if (economicalBuildings.get(i) instanceof Market && type.equals("Market")) {
						existing = true;
					}
				}
				if (!existing) {
					city.getEconomicalBuildings().add((EconomicBuilding) building);
					treasury -= building.getCost();
				}
			}
		}
	}

	public void upgradeBuilding(Building b)
			throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException {
		if (treasury < b.getUpgradeCost()) {
			throw new NotEnoughGoldException();
		}
		if (b.getLevel() == 3) {
			throw new MaxLevelException();
		}
		if (b.isCoolDown()) {
			throw new BuildingInCoolDownException();
		}
		treasury -= b.getUpgradeCost();
		b.upgrade();
	}

	public void initiateArmy(City city, Unit unit) {
		Army attackingArmy = new Army(city.getName());
		attackingArmy.add(unit);
		unit.setParentArmy(attackingArmy);
		city.getDefendingArmy().remove(unit);
		controlledArmies.add(attackingArmy);
	}

	public void laySiege(Army army, City city) throws TargetNotReachedException, FriendlyCityException {
		if (!army.getCurrentLocation().equals(city.getName())) {
			throw new TargetNotReachedException();
		}
		if (controlledCities.contains(city)) {
			throw new FriendlyCityException();
		}
		army.setCurrentStatus(Status.BESIEGING);
		city.setUnderSiege(true);
	}

}
