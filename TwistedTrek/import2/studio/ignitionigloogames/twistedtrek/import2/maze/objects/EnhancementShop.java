/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class EnhancementShop extends AbstractShop {
    // Constructors
    public EnhancementShop() {
	super(ShopTypes.SHOP_TYPE_ENHANCEMENTS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_ENHANCEMENT_SHOP;
    }

    @Override
    public String getName() {
	return "Enhancement Shop";
    }

    @Override
    public String getPluralName() {
	return "Enhancement Shops";
    }

    @Override
    public String getDescription() {
	return "Enhancement Shops sell improvements to weapons and armor.";
    }
}
