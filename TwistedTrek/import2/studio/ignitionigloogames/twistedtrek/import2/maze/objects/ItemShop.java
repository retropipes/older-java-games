/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class ItemShop extends AbstractShop {
    // Constructors
    public ItemShop() {
	super(ShopTypes.SHOP_TYPE_ITEMS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_ITEM_SHOP;
    }

    @Override
    public String getName() {
	return "Item Shop";
    }

    @Override
    public String getPluralName() {
	return "Item Shops";
    }

    @Override
    public String getDescription() {
	return "Item Shops sell items used in battle.";
    }
}
