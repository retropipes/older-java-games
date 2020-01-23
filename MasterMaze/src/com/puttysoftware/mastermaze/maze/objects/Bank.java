/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.items.ShopTypes;
import com.puttysoftware.mastermaze.maze.generic.GenericShop;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Bank extends GenericShop {
    // Constructors
    public Bank() {
        super(ShopTypes.SHOP_TYPE_BANK);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BANK;
    }

    @Override
    public String getName() {
        return "Bank";
    }

    @Override
    public String getPluralName() {
        return "Banks";
    }

    @Override
    public String getDescription() {
        return "Banks store money for safe keeping.";
    }
}
