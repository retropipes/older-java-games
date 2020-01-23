/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractShop;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.shops.ShopTypes;

public class Regenerator extends AbstractShop {
    // Constructors
    public Regenerator() {
	super(ShopTypes.SHOP_TYPE_REGENERATOR);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_REGENERATOR;
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
