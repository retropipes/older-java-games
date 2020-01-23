/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class WeaponsShop extends AbstractShop {
    // Constructors
    public WeaponsShop() {
	super(ShopTypes.SHOP_TYPE_WEAPONS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_WEAPONS_SHOP;
    }

    @Override
    public String getName() {
	return "Weapons Shop";
    }

    @Override
    public String getPluralName() {
	return "Weapons Shops";
    }

    @Override
    public String getDescription() {
	return "Weapons Shops sell weapons used to fight monsters.";
    }
}
