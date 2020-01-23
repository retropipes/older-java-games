/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.items.Shop;

public abstract class AbstractShop extends AbstractDungeonObject {
    // Fields
    private final int shopType;

    // Constructors
    public AbstractShop(int newShopType) {
        super(false, false);
        this.setTemplateColor(ColorConstants.COLOR_ORANGE);
        this.shopType = newShopType;
    }

    // Methods
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SHOP);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Shop shop = DungeonDiver4.getApplication()
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
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantity(Dungeon dungeon) {
        return (int) Math.ceil(Math.sqrt(dungeon.getRows()
                * dungeon.getColumns() / 2));
    }

    @Override
    public int getMaximumRequiredQuantity(Dungeon dungeon) {
        return (int) Math.floor(Math.sqrt(dungeon.getRows()
                * dungeon.getColumns()));
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
