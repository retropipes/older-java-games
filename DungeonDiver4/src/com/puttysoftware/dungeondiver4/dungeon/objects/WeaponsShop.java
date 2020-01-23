/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractShop;
import com.puttysoftware.dungeondiver4.items.ShopTypes;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

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
