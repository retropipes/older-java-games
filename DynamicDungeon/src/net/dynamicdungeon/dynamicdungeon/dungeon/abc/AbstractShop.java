/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;
import net.dynamicdungeon.dynamicdungeon.shops.Shop;

public abstract class AbstractShop extends AbstractDungeonObject {
    // Fields
    private final int shopType;

    // Constructors
    public AbstractShop(final int newShopType) {
        super(false, false);
        this.shopType = newShopType;
    }

    // Methods
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SHOP);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Shop shop = DynamicDungeon.getApplication()
                .getGenericShop(this.shopType);
        if (shop != null) {
            shop.showShop();
        }
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
