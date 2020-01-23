/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GenericShop;

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
