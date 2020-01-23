/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.items.ShopTypes;
import com.puttysoftware.gemma.support.map.generic.GenericShop;

public class EnhancementShop extends GenericShop {
    // Constructors
    public EnhancementShop() {
        super(ShopTypes.SHOP_TYPE_ENHANCEMENTS);
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
