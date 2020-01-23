/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.items.ShopTypes;
import com.puttysoftware.gemma.support.map.generic.GenericShop;

public class Regenerator extends GenericShop {
    // Constructors
    public Regenerator() {
        super(ShopTypes.SHOP_TYPE_REGENERATOR);
    }

    @Override
    public String getName() {
        return "Regenerator";
    }

    @Override
    public String getPluralName() {
        return "Regenerators";
    }

    @Override
    public String getDescription() {
        return "Regenerators restore magic, for a fee.";
    }
}
