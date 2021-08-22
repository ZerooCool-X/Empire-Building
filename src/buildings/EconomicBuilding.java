package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

abstract public class EconomicBuilding extends Building{

	public EconomicBuilding(int cost, int upgradeCost) {
		super(cost, upgradeCost);
	}

	public abstract int harvest();

	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {

		super.upgrade();
	}

}
