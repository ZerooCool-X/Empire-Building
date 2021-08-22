package buildings;

import exceptions.*;
import units.*;

public class Barracks extends MilitaryBuilding {
	public Barracks() {
		super(2000, 1000, 500);
	}

	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if (this.isCoolDown()) {
			throw new BuildingInCoolDownException();
		}

		if (this.getCurrentRecruit() >= this.getMaxRecruit()) {
			throw new MaxRecruitedException();
		} else {
			this.setCurrentRecruit(this.getCurrentRecruit() + 1);
		}
		if (this.getLevel() == 1) {
			return new Infantry(1, 50, 0.5, 0.6, 0.7);
		} else if (this.getLevel() == 2) {
			return new Infantry(2, 50, 0.5, 0.6, 0.7);
		} else {
			return new Infantry(3, 60, 0.6, 0.7, 0.8);
		}

	}
	
	public Unit create() {
		if (this.getLevel() == 1) {
			return new Infantry(1, 50, 0.5, 0.6, 0.7);
		} else if (this.getLevel() == 2) {
			return new Infantry(2, 50, 0.5, 0.6, 0.7);
		} else {
			return new Infantry(3, 60, 0.6, 0.7, 0.8);
		}
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
	}
	
}
