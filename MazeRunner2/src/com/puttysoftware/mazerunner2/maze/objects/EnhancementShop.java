/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.items.ShopTypes;
import com.puttysoftware.mazerunner2.maze.abc.AbstractShop;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class EnhancementShop extends AbstractShop {
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
