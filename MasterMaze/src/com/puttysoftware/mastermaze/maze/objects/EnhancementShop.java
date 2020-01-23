/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.items.ShopTypes;
import com.puttysoftware.mastermaze.maze.generic.GenericShop;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class EnhancementShop extends GenericShop {
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
