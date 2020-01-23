/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractShop;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.shops.ShopTypes;

public class WeaponsShop extends AbstractShop {
    // Constructors
    public WeaponsShop() {
	super(ShopTypes.SHOP_TYPE_WEAPONS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_WEAPONS_SHOP;
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
