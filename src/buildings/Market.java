package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Market extends EconomicBuilding {

	public Market() {
		super(1500, 700);
	}
	public int harvest() {

		if(this.getLevel()==1) {
			return 1000;
		}else if(this.getLevel()==2) {
			return 1500;
		}else{
			return 2000;
		}
	}

	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
	}

	public String tosString() {
		return "Market " + super.toString();
	}
	
}
