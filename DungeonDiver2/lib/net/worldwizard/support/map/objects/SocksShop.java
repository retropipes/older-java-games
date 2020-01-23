/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GenericShop;

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
