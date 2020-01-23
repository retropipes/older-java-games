package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericShop;
import net.worldwizard.worldz.items.ShopTypes;

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
