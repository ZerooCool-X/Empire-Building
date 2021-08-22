package units;

import java.util.*;

import exceptions.*;

public class Army {
	private Status currentStatus;
	private ArrayList<Unit> units;
	private int distancetoTarget;
	private String target;
	private String currentLocation;
	private final int maxToHold = 10;
	public Status getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}
	public ArrayList<Unit> getUnits() {
		return units;
	}
	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}
	public int getDistancetoTarget() {
		return distancetoTarget;
	}
	public void setDistancetoTarget(int distancetoTarget) {
		this.distancetoTarget = distancetoTarget;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public int getMaxToHold() {
		return maxToHold;
	}

	public void add(Unit u) {
		units.add(u);
	}
	
	public void remove(Unit u) {
		units.remove(u);
	}
	
	public Army(String currentLocation) {
		this.currentLocation = currentLocation;
		units = new ArrayList<Unit>();
		distancetoTarget = -1;
		target = "";
		currentStatus = Status.IDLE;
	}
	
	public void relocateUnit(Unit unit) throws MaxCapacityException {
		if (units.size() >= maxToHold) {
			throw new MaxCapacityException();
		}
		unit.getParentArmy().remove(unit);
		add(unit);
		unit.setParentArmy(this);
	}
	
	public void handleAttackedUnit(Unit u) {
		if (u.getCurrentSoldierCount() <= 0) {
			remove(u);
			u.setParentArmy(null);
		}
	}
	
	public double foodNeeded() {
		double food = 0;
		for(Unit u : units) {
			if (currentStatus == Status.BESIEGING) {
				food += u.getCurrentSoldierCount() * u.getSiegeUpkeep();
			}else if (currentStatus == Status.IDLE) {
				food += u.getCurrentSoldierCount() * u.getIdleUpkeep(); 
			}else if (currentStatus == Status.MARCHING) {
				food += u.getCurrentSoldierCount() * u.getMarchingUpkeep();
			}
		}
		return food;
	}
}
