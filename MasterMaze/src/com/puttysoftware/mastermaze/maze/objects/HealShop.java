/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.items.ShopTypes;
import com.puttysoftware.mastermaze.maze.generic.GenericShop;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class HealShop extends GenericShop {
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
