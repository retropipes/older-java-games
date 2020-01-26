/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.abc;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.maze.MazeConstants;
import com.puttysoftware.tallertower.maze.utilities.TypeConstants;
import com.puttysoftware.tallertower.shops.Shop;

public abstract class AbstractShop extends AbstractMazeObject {
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
        final Shop shop = TallerTower.getApplication()
                .getGenericShop(this.shopType);
        if (shop != null) {
            shop.showShop();
        }
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
