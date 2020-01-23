/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.items.ShopTypes;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericShop;

public class WeaponsShop extends GenericShop {
    // Constructors
    public WeaponsShop() {
        super(ShopTypes.SHOP_TYPE_WEAPONS);
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
