/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.items.ShopTypes;
import com.puttysoftware.gemma.support.map.generic.GenericShop;

public class HealShop extends GenericShop {
    // Constructors
    public HealShop() {
        super(ShopTypes.SHOP_TYPE_HEALER);
    }

    @Override
    public String getName() {
        return "Heal Shop";
    }

    @Override
    public String getPluralName() {
        return "Heal Shops";
    }

    @Override
    public String getDescription() {
        return "Heal Shops restore health, for a fee.";
    }
}
