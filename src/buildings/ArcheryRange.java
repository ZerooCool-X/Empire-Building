package buildings;

import exceptions.*;
import units.*;

public class ArcheryRange extends MilitaryBuilding {

	public ArcheryRange() {
		super(1500, 800, 400);
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
			return new Archer(1, 60, 0.4, 0.5, 0.6);
		} else if (this.getLevel() == 2) {
			return new Archer(2, 60, 0.4, 0.5, 0.6);
		} else {
			return new Archer(3, 70, 0.5, 0.6, 0.7);
		}

	}
	
	public Unit create() {
		if (this.getLevel() == 1) {
			return new Archer(1, 60, 0.4, 0.5, 0.6);
		} else if (this.getLevel() == 2) {
			return new Archer(2, 60, 0.4, 0.5, 0.6);
		} else {
			return new Archer(3, 70, 0.5, 0.6, 0.7);
		}
	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
	}

}
