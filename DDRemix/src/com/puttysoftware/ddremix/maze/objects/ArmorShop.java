/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.maze.abc.AbstractShop;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.shops.ShopTypes;

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
