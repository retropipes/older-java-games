/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.items.ShopTypes;
import com.puttysoftware.mastermaze.maze.generic.GenericShop;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class ItemShop extends GenericShop {
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
