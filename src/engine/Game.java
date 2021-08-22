package engine;

import java.io.*;
import java.util.*;

import buildings.*;
import units.*;
import exceptions.*;

public class Game {

	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	final private int maxTurnCount = 50;
	private int currentTurnCount;

	public Game(String playerName, String playerCity) throws IOException {
		player = new Player(playerName);
		City city1 = new City(playerCity);
		availableCities = new ArrayList<>();
		availableCities.add(city1);
		player.add(city1);
		currentTurnCount = 1;
		distances = new ArrayList<>();

		loadCitiesAndDistances();
		initializeDefendingCities(playerCity);
	}

	private void loadCitiesAndDistances() throws IOException {
		FileReader fileReader = new FileReader("distances.CSV");
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine = "";
		while ((currentLine = br.readLine()) != null) {
			String[] arr = currentLine.split(",");
			distances.add(new Distance(arr[0], arr[1], Integer.parseInt(arr[2])));
			boolean f = true;

			for (int i = 0; i < availableCities.size(); i++) {

				if (availableCities.get(i).getName().equals(arr[0])) {
					f = false;
				}
			}
			if (f) {
				availableCities.add(new City(arr[0]));
			}
			f = true;
			for (int i = 0; i < availableCities.size(); i++) {
				if (availableCities.get(i).getName().equals(arr[1])) {
					f = false;
				}
			}
			if (f) {
				availableCities.add(new City(arr[1]));
			}
		}

	}

	private void initializeDefendingCities(String cityName) throws IOException {

		for (int i = 0; i < availableCities.size(); i++) {
			if (!availableCities.get(i).getName().equals(cityName)) {
				loadArmy(availableCities.get(i).getName(),
						availableCities.get(i).getName().toLowerCase() + "_army.csv");
			}

		}

	}

	public void loadArmy(String cityName, String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine = "";
		Army army = new Army(cityName);
		while ((currentLine = br.readLine()) != null) {
			String[] arr = currentLine.split(",");
			int level = Integer.parseInt(arr[1]);
			Unit u = null;
			if (arr[0].equals("Archer")) {
				if (level == 1) {
					army.add(u = new Archer(1, 60, 0.4, 0.5, 0.6));
				} else if (level == 2) {
					army.add(u = new Archer(2, 60, 0.4, 0.5, 0.6));
				} else if (level == 3) {
					army.add(u = new Archer(3, 70, 0.5, 0.6, 0.7));
				}
			} else if (arr[0].equals("Infantry")) {
				if (level == 1) {
					army.add(u = new Infantry(1, 50, 0.5, 0.6, 0.7));
				} else if (level == 2) {
					army.add(u = new Infantry(2, 50, 0.5, 0.6, 0.7));
				} else if (level == 3) {
					army.add(u = new Infantry(3, 60, 0.6, 0.7, 0.8));
				}
			} else if (arr[0].equals("Cavalry")) {
				if (level == 1) {
					army.add(u = new Cavalry(1, 40, 0.6, 0.7, 0.75));
				} else if (level == 2) {
					army.add(u = new Cavalry(2, 40, 0.6, 0.7, 0.75));
				} else if (level == 3) {
					army.add(u = new Cavalry(3, 60, 0.7, 0.8, 0.9));
				}
			}
			u.setParentArmy(army);
		}
		for (int i = 0; i < availableCities.size(); i++) {
			if (availableCities.get(i).getName().equals(cityName)) {
				availableCities.get(i).setDefendingArmy(army);
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	// ------------------------------------------------------- M2

	public void targetCity(Army army, String targetName) {
		if (army.getCurrentStatus().equals(Status.MARCHING)) {
			return;
		}
		for (int i = 0; i < distances.size(); i++) {
			Distance d = distances.get(i);
			if ((d.getFrom().equals(army.getCurrentLocation()) && d.getTo().equals(targetName))
					|| (d.getFrom().equals(targetName) && d.getTo().equals(army.getCurrentLocation()))) {
				army.setDistancetoTarget(d.getDistance());
				army.setCurrentLocation("On Road");
				army.setCurrentStatus(Status.MARCHING);
				army.setTarget(targetName);
				break;
			}
		}

	}

	public void endTurn() {
		currentTurnCount++;
		for (int i = 0; i < availableCities.size(); i++) {
			City city = availableCities.get(i);
			if (city.isUnderSiege()) {
				city.setTurnsUnderSiege(city.getTurnsUnderSiege() + 1);
				ArrayList<Unit> units = city.getDefendingArmy().getUnits();
				for (int j = 0; j < units.size(); j++) {
					units.get(j).setCurrentSoldierCount((int) (units.get(j).getCurrentSoldierCount() * 0.9));
				}
			}
		}
		for (int i = 0; i < player.getControlledCities().size(); i++) {
			City city = player.getControlledCities().get(i);
			ArrayList<MilitaryBuilding> militaryBuildings = city.getMilitaryBuildings();
			for (int j = 0; j < militaryBuildings.size(); j++) {
				militaryBuildings.get(j).setCoolDown(false);
				militaryBuildings.get(j).setCurrentRecruit(0);
			}
			ArrayList<EconomicBuilding> economicBuildings = city.getEconomicalBuildings();
			for (int j = 0; j < economicBuildings.size(); j++) {
				economicBuildings.get(j).setCoolDown(false);
				if (economicBuildings.get(j) instanceof Market) {
					player.setTreasury(player.getTreasury() + economicBuildings.get(j).harvest());
				} else {
					player.setFood(player.getFood() + economicBuildings.get(j).harvest());
				}
			}
		}
		double requiredFood = 0;
		for (City c : player.getControlledCities()) {
			requiredFood += c.getDefendingArmy().foodNeeded();
		}
//		System.out.println(player.getControlledArmies());
		for (int i = 0; i < player.getControlledArmies().size(); i++) {
//			System.out.println("*");
			Army army = player.getControlledArmies().get(i);
			requiredFood += army.foodNeeded();
			if (!army.getTarget().equals("") && army.getDistancetoTarget() != -1) {
				army.setDistancetoTarget(army.getDistancetoTarget() - 1);
				if (army.getDistancetoTarget() <= 0) {
					army.setCurrentStatus(Status.IDLE);
					army.setCurrentLocation(army.getTarget());
					army.setTarget("");
					army.setDistancetoTarget(-1);
				}
			}
		}
		if (requiredFood > player.getFood()) {
			player.setFood(0);
			for (int i = 0; i < player.getControlledArmies().size(); i++) {
				ArrayList<Unit> units = player.getControlledArmies().get(i).getUnits();
				for (int j = 0; j < units.size(); j++) {
					units.get(j).setCurrentSoldierCount((int) (units.get(j).getCurrentSoldierCount() * 0.9));
				}
			}
			for (City c : player.getControlledCities()) {
				ArrayList<Unit> units = c.getDefendingArmy().getUnits();
				for (int j = 0; j < units.size(); j++) {
					units.get(j).setCurrentSoldierCount((int) (units.get(j).getCurrentSoldierCount() * 0.9));
				}
			}
		} else {
			player.setFood(player.getFood() - requiredFood);
		}

	}

	public void occupy(Army a, String cityName) {
		City city = null;
		for (int i = 0; i < availableCities.size(); i++) {
			if (availableCities.get(i).getName().equals(cityName)) {
				city = availableCities.get(i);
				break;
			}
		}
		player.getControlledCities().add(city);
		city.setDefendingArmy(a);
		player.getControlledArmies().remove(a);
		for (Army army : player.getControlledArmies()) {
			if (army.getCurrentLocation().equals(city.getName()) && army.getCurrentStatus() == Status.BESIEGING) {
				army.setCurrentStatus(Status.IDLE);
			}
		}
		city.setTurnsUnderSiege(-1);
		city.setUnderSiege(false);
	}

	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
		if (player.getControlledArmies().contains(attacker) && player.getControlledArmies().contains(defender))
			throw new FriendlyFireException();
		while (attacker.getUnits().size() > 0 && defender.getUnits().size() > 0) {
			Unit attack = attacker.getUnits().get((int) (Math.random() * attacker.getUnits().size()));
			Unit defend = defender.getUnits().get((int) (Math.random() * defender.getUnits().size()));
			boolean turn = true;
			while (attack.getCurrentSoldierCount() > 0 && defend.getCurrentSoldierCount() > 0) {
				if (turn)
					attack.attack(defend);
				else
					defend.attack(attack);
				turn = !turn;
			}
		}
		if (attacker.getUnits().size() > 0) {
			occupy(attacker, defender.getCurrentLocation());
		}
	}

	public boolean isGameOver() {
		return currentTurnCount > maxTurnCount || player.getControlledCities().size() == availableCities.size();
	}
}
