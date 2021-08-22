package buildings;

import exceptions.*;
import units.*;

public class Stable extends MilitaryBuilding {

	public Stable() {
		super(2500, 1500, 600);
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
			return new Cavalry(1, 40, 0.6, 0.7, 0.75);
		} else if (this.getLevel() == 2) {
			return new Cavalry(2, 40, 0.6, 0.7, 0.75);
		} else {
			return new Cavalry(3, 60, 0.7, 0.8, 0.9);
		}

	}
	public Unit create() {
		if (this.getLevel() == 1) {
			return new Cavalry(1, 40, 0.6, 0.7, 0.75);
		} else if (this.getLevel() == 2) {
			return new Cavalry(2, 40, 0.6, 0.7, 0.75);
		} else {
			return new Cavalry(3, 60, 0.7, 0.8, 0.9);
		}
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {

		super.upgrade();
	}
	public String tosString() {
		return "Stable " + super.toString();
	}
}
