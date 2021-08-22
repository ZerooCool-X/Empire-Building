package buildings;

import exceptions.*;

abstract public class Building {

	private int cost = 0, level = 1, upgradeCost = 0;

	private boolean coolDown = true;

	public Building(int cost, int upgradeCost) {
		this.cost = cost;
		this.upgradeCost = upgradeCost;
	}

	public int getCost() {
		return cost;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUpgradeCost() {
		return upgradeCost;
	}

	public void setUpgradeCost(int upgradeCost) {
		this.upgradeCost = upgradeCost;
	}

	public boolean isCoolDown() {
		return coolDown;
	}

	public void setCoolDown(boolean coolDown) {
		this.coolDown = coolDown;
	}

	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		if (coolDown) {
			throw new BuildingInCoolDownException();
		}
		if (this instanceof ArcheryRange) {
			if (this.level < 3) {
				setUpgradeCost(700);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Barracks) {
			if (this.level < 3) {
				setUpgradeCost(1500);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Stable) {
			if (this.level < 3) {
				setUpgradeCost(2000);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Farm) {
			if (this.level < 3) {
				setUpgradeCost(700);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Market) {
			if (this.level < 3) {
				setUpgradeCost(1000);
			} else {
				throw new MaxLevelException();
			}

		}
		level++;
		coolDown = true;
	}

	public String toString() {
		String r;
		if (this instanceof ArcheryRange) {
			r = "ArcheryRange";
		} else if (this instanceof Barracks) {
			r = "Barracks";
		} else if (this instanceof Stable) {
			r = "Stable";
		} else if (this instanceof Market) {
			r = "Market";
		} else {
			r = "Farm";
		}
		return r + " " + "lvl " + level + "upgrade cost " + upgradeCost + " "
				+ (isCoolDown() ? "under cooldown" : "available");
	}

}
