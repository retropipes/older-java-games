/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.items.ShopTypes;
import com.puttysoftware.gemma.support.map.generic.GenericShop;

public class ArmorShop extends GenericShop {
    // Constructors
    public ArmorShop() {
        super(ShopTypes.SHOP_TYPE_ARMOR);
    }

    @Override
    public String getName() {
        return "Armor Shop";
    }

    @Override
    public String getPluralName() {
        return "Armor Shops";
    }

    @Override
    public String getDescription() {
        return "Armor Shops sell protective armor.";
    }
}
