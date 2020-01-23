/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.items.ShopTypes;
import com.puttysoftware.mazerunner2.maze.abc.AbstractShop;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class SocksShop extends AbstractShop {
    // Constructors
    public SocksShop() {
        super(ShopTypes.SHOP_TYPE_SOCKS);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SOCKS_SHOP;
    }

    @Override
    public String getName() {
        return "Socks Shop";
    }

    @Override
    public String getPluralName() {
        return "Socks Shops";
    }

    @Override
    public String getDescription() {
        return "Socks Shops sell enchanted socks that act as you walk.";
    }
}
