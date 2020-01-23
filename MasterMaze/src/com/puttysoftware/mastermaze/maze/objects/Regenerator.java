/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.items.ShopTypes;
import com.puttysoftware.mastermaze.maze.generic.GenericShop;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Regenerator extends GenericShop {
    // Constructors
    public Regenerator() {
        super(ShopTypes.SHOP_TYPE_REGENERATOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_REGENERATOR;
    }

    @Override
    public String getName() {
        return "Regenerator";
    }

    @Override
    public String getPluralName() {
        return "Regenerators";
    }

    @Override
    public String getDescription() {
        return "Regenerators restore magic, for a fee.";
    }
}
