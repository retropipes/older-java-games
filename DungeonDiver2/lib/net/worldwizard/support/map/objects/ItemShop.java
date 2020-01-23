/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GenericShop;

public class ItemShop extends GenericShop {
    // Constructors
    public ItemShop() {
        super(ShopTypes.SHOP_TYPE_ITEMS);
    }

    @Override
    public String getName() {
        return "Item Shop";
    }

    @Override
    public String getPluralName() {
        return "Item Shops";
    }

    @Override
    public String getDescription() {
        return "Item Shops sell items used in battle.";
    }
}
