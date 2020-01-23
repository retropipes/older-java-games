package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericShop;
import net.worldwizard.worldz.items.ShopTypes;

public class WeaponsShop extends GenericShop {
    // Constructors
    public WeaponsShop() {
        super(ShopTypes.SHOP_TYPE_WEAPONS);
    }

    @Override
    public String getName() {
        return "Weapons Shop";
    }

    @Override
    public String getPluralName() {
        return "Weapons Shops";
    }

    @Override
    public String getDescription() {
        return "Weapons Shops sell weapons used to fight monsters.";
    }
}
