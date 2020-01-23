/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class SocksShop extends AbstractShop {
    // Constructors
    public SocksShop() {
	super(ShopTypes.SHOP_TYPE_SOCKS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_SOCKS_SHOP;
    }

    @Override
    public String getName() {
	return "Socks Shop";
    }

    @Override
    public String getPluralName() {
	return "Socks Shops";
    }

    @Override
    public String getDescription() {
	return "Socks Shops sell enchanted socks that act as you walk.";
    }
}
