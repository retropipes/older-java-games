package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericShop;
import net.worldwizard.worldz.items.ShopTypes;

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
