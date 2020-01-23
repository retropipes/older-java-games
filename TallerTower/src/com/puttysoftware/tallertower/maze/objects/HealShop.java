/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.maze.abc.AbstractShop;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.shops.ShopTypes;

public class HealShop extends AbstractShop {
    // Constructors
    public HealShop() {
        super(ShopTypes.SHOP_TYPE_HEALER);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HEAL_SHOP;
    }

    @Override
    public String getName() {
        return "Heal Shop";
    }

    @Override
    public String getPluralName() {
        return "Heal Shops";
    }

    @Override
    public String getDescription() {
        return "Heal Shops restore health, for a fee.";
    }
}
