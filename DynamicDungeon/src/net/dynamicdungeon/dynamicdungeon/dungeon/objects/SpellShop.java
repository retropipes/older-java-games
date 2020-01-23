/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractShop;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.shops.ShopTypes;

public class SpellShop extends AbstractShop {
    // Constructors
    public SpellShop() {
	super(ShopTypes.SHOP_TYPE_SPELLS);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_SPELL_SHOP;
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
