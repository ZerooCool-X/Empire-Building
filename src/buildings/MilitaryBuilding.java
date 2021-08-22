package buildings;

import exceptions.*;
import units.*;

abstract public class MilitaryBuilding extends Building {

	private int recruitmentCost = 0, currentRecruit = 0;
	private final int maxRecruit = 3;
	
	public MilitaryBuilding(int cost, int upgradeCost, int recruitmentCost) {
		super(cost, upgradeCost);
		this.recruitmentCost = recruitmentCost;
//		setCoolDown(false);
	}

	public int getRecruitmentCost() {
		return recruitmentCost;
	}

	public void setRecruitmentCost(int recruitmentCost) {
		this.recruitmentCost = recruitmentCost;
	}

	public int getCurrentRecruit() {
		return currentRecruit;
	}

	public void setCurrentRecruit(int currentRecruit) {
		this.currentRecruit = currentRecruit;
	}

	public int getMaxRecruit() {
		return maxRecruit;
	}

	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		
		if (this instanceof ArcheryRange) {

			if (this.getLevel() == 1) {

				setRecruitmentCost(450);
			} else if (this.getLevel() == 2) {
				setRecruitmentCost(500);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Barracks) {

			if (this.getLevel() == 1) {

				setRecruitmentCost(550);
			} else if (this.getLevel() == 2) {
				setRecruitmentCost(600);
			} else {
				throw new MaxLevelException();
			}

		}
		if (this instanceof Stable) {

			if (this.getLevel() == 1) {

				setRecruitmentCost(650);
			} else if (this.getLevel() == 2) {
				setRecruitmentCost(700);
			} else {
				throw new MaxLevelException();
			}

		}
		super.upgrade();

	}
	public abstract Unit recruit()  throws BuildingInCoolDownException, MaxRecruitedException;
	
	public abstract Unit create();
	
	public String toString() {
		return super.toString() + " " + "recruitment cost " + recruitmentCost;
	}

}
