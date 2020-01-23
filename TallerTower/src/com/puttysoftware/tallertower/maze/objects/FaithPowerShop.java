/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.maze.abc.AbstractShop;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.shops.ShopTypes;

public class FaithPowerShop extends AbstractShop {
    // Constructors
    public FaithPowerShop() {
        super(ShopTypes.SHOP_TYPE_FAITH_POWERS);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FAITH_POWER_SHOP;
    }

    @Override
    public String getName() {
        return "Faith Power Shop";
    }

    @Override
    public String getPluralName() {
        return "Faith Power Shops";
    }

    @Override
    public String getDescription() {
        return "Faith Power Shops will imbue your equipment with the power of your faith, for a fee.";
    }
}
