/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class Bank extends AbstractShop {
    // Constructors
    public Bank() {
	super(ShopTypes.SHOP_TYPE_BANK);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_BANK;
    }

    @Override
    public String getName() {
	return "Bank";
    }

    @Override
    public String getPluralName() {
	return "Banks";
    }

    @Override
    public String getDescription() {
	return "Banks store money for safe keeping.";
    }
}
