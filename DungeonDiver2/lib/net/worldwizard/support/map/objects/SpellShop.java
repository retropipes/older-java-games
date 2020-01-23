package net.worldwizard.support.map.objects;

import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GenericShop;

public class SpellShop extends GenericShop {
    // Constructors
    public SpellShop() {
        super(ShopTypes.SHOP_TYPE_SPELLS);
    }

    @Override
    public String getName() {
        return "Spell Shop";
    }

    @Override
    public String getPluralName() {
        return "Spell Shops";
    }

    @Override
    public String getDescription() {
        return "Spell Shops teach spells, for a fee.";
    }
}
