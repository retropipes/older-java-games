/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.items.ShopTypes;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericShop;

public class Bank extends GenericShop {
    // Constructors
    public Bank() {
        super(ShopTypes.SHOP_TYPE_BANK);
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
