/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.items.Shop;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractShop extends AbstractMazeObject {
    // Fields
    private final int shopType;

    // Constructors
    public AbstractShop(final int newShopType) {
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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        if (PreferencesManager.getRPGEnabled()) {
            final Shop shop = MazeRunnerII.getApplication()
                    .getGenericShop(this.shopType);
            if (shop != null) {
                shop.showShop();
            }
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
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

    @Override
    public boolean enabledInBattle() {
        return false;
    }

    @Override
    public AbstractMazeObject gameRenderHook(final int x, final int y,
            final int z) {
        if (PreferencesManager.getRPGEnabled()) {
            return this;
        } else {
            return new Empty();
        }
    }
}
