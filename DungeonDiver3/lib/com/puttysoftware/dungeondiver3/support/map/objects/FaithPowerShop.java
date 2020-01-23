/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.items.ShopTypes;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericShop;

public class FaithPowerShop extends GenericShop {
    // Constructors
    public FaithPowerShop() {
        super(ShopTypes.SHOP_TYPE_FAITH_POWERS);
    }

    @Override
    public String getName() {
        return "Faith Power Shop";
    }

    @Override
    public String getPluralName() {
        return "Faith Power Shops";
    }

    @Override
    public String getDescription() {
        return "Faith Power Shops will imbue your equipment with the power of your faith, for a fee.";
    }
}
