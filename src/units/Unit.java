package units;

import buildings.ArcheryRange;
import buildings.Barracks;
import exceptions.FriendlyFireException;

abstract public class Unit {
	private int level, maxSoldierCount, currentSoldierCount;
	private double idleUpkeep, marchingUpkeep, siegeUpkeep;
	private Army parentArmy;

	public Army getParentArmy() {
		return parentArmy;
	}

	public void setParentArmy(Army parentArmy) {
		this.parentArmy = parentArmy;
	}

	public int getCurrentSoldierCount() {
		return currentSoldierCount;
	}

	public void setCurrentSoldierCount(int currentSoldierCount) {
		this.currentSoldierCount = currentSoldierCount;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxSoldierCount() {
		return maxSoldierCount;
	}

	public double getIdleUpkeep() {
		return idleUpkeep;
	}

	public double getMarchingUpkeep() {
		return marchingUpkeep;
	}

	public double getSiegeUpkeep() {
		return siegeUpkeep;
	}

	public String toString() {
		String s;
		if (this instanceof Archer) {
			s = "Archer";
		} else if (this instanceof Infantry) {
			s = "Infantry";
		} else {
			s = "Cavalry";
		}
		return "<html>" + s + "  level: " + level + "<br/>soldier count: " + currentSoldierCount;
	}

	public Unit(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		this.level = level;
		this.maxSoldierCount = maxSoldierCount;
		this.idleUpkeep = idleUpkeep;
		this.marchingUpkeep = marchingUpkeep;
		this.siegeUpkeep = siegeUpkeep;
		currentSoldierCount = maxSoldierCount;
	}

	public void attack(Unit target) throws FriendlyFireException {
		if (target.parentArmy.equals(parentArmy)) {
			throw new FriendlyFireException();
		}
		if (this instanceof Archer) {
			if (level == 1) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.1 * currentSoldierCount);
				}
			} else if (level == 2) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.4 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.1 * currentSoldierCount);
				}
			} else if (level == 3) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.5 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.4 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				}
			}
		} else if (this instanceof Infantry) {
			if (level == 1) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.1 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.1 * currentSoldierCount);
				}
			} else if (level == 2) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.4 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				}
			} else if (level == 3) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.5 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.25 * currentSoldierCount);
				}
			}
		} else if (this instanceof Cavalry) {
			if (level == 1) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.5 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				}
			} else if (level == 2) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.6 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.4 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.2 * currentSoldierCount);
				}
			} else if (level == 3) {
				if (target instanceof Archer) {
					target.currentSoldierCount -= (int) (0.7 * currentSoldierCount);
				} else if (target instanceof Infantry) {
					target.currentSoldierCount -= (int) (0.5 * currentSoldierCount);
				} else if (target instanceof Cavalry) {
					target.currentSoldierCount -= (int) (0.3 * currentSoldierCount);
				}
			}
		}
		target.currentSoldierCount = Math.max(0, target.currentSoldierCount);
		target.parentArmy.handleAttackedUnit(target);
	}

}
