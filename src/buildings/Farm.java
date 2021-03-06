package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

	public Farm() {
		super(1000, 500);
	}

	public int harvest() {

		if(this.getLevel()==1) {
			return 500;
		}else if(this.getLevel()==2) {
			return 700;
		}else{
			return 1000;
		}
	}
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
	}
	
	public String tosString() {
		return "Farm " + super.toString();
	}

}
