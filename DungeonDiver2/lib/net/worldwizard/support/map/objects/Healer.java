package net.worldwizard.support.map.objects;

import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GenericShop;

public class Healer extends GenericShop {
    // Constructors
    public Healer() {
        super(ShopTypes.SHOP_TYPE_HEALER);
    }

    @Override
    public String getName() {
        return "Healer";
    }

    @Override
    public String getPluralName() {
        return "Healers";
    }

    @Override
    public String getDescription() {
        return "Healers restore health, for a fee.";
    }
}
