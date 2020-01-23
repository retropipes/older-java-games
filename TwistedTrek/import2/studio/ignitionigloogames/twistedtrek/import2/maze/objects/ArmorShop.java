/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class ArmorShop extends AbstractShop {
    // Constructors
    public ArmorShop() {
	super(ShopTypes.SHOP_TYPE_ARMOR);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_ARMOR_SHOP;
    }

    @Override
    public String getName() {
	return "Armor Shop";
    }

    @Override
    public String getPluralName() {
	return "Armor Shops";
    }

    @Override
    public String getDescription() {
	return "Armor Shops sell protective armor.";
    }
}
