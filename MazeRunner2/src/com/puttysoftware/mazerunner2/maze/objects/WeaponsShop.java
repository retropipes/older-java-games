/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.items.ShopTypes;
import com.puttysoftware.mazerunner2.maze.abc.AbstractShop;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class WeaponsShop extends AbstractShop {
    // Constructors
    public WeaponsShop() {
        super(ShopTypes.SHOP_TYPE_WEAPONS);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WEAPONS_SHOP;
    }

    @Override
    public String getName() {
        return "Weapons Shop";
    }

    @Override
    public String getPluralName() {
        return "Weapons Shops";
    }

    @Override
    public String getDescription() {
        return "Weapons Shops sell weapons used to fight monsters.";
    }
}
