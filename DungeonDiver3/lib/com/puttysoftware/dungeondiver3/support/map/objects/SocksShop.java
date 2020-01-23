/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.items.ShopTypes;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericShop;

public class SocksShop extends GenericShop {
    // Constructors
    public SocksShop() {
        super(ShopTypes.SHOP_TYPE_SOCKS);
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
