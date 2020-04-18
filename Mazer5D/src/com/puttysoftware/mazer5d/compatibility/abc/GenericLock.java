/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.maze.MazeConstants;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazer5d.compatibility.objects.GhostAmulet;
import com.puttysoftware.mazer5d.compatibility.objects.PasswallBoots;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public abstract class GenericLock extends MazeObjectModel {
    // Fields
    private GenericKey key;
    protected static final long SCORE_UNLOCK = 30L;

    // Constructors
    protected GenericLock(final GenericKey mgk) {
        super(true);
        this.key = mgk;
    }

    protected GenericLock(final GenericKey mgk,
            final boolean doesAcceptPushInto) {
        super(true, false, doesAcceptPushInto, false, false, false, false, true,
                false, 0);
        this.key = mgk;
    }

    protected GenericLock(final boolean isSolid, final GenericKey mgk) {
        super(isSolid);
        this.key = mgk;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericLock other = (GenericLock) obj;
        if (this.key != other.key
                && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }

    @Override
    public GenericLock clone() {
        final GenericLock copy = (GenericLock) super.clone();
        copy.key = this.key.clone();
        return copy;
    }

    // Accessor methods
    public GenericKey getKey() {
        return this.key;
    }

    protected void setKey(final GenericKey newKey) {
        this.key = newKey;
    }

    // Scriptability
    @Override
    public abstract void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv);

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        if (!app.getGameManager()
                .isEffectActive(MazeEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (!this.key.isInfinite()) {
                inv.removeItem(this.key);
            }
            app.getGameManager().decay();
            SoundPlayer.playSound(SoundIndex.UNLOCK, SoundGroup.GAME);
            Mazer5D.getBagOStuff().getGameManager()
                    .addToScore(GenericLock.SCORE_UNLOCK);
        } else {
            SoundPlayer.playSound(SoundIndex.WALK, SoundGroup.GAME);
        }
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return !inv.isItemThere(this.key);
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        // Handle passwall boots and ghost amulet
        if (inv.isItemThere(new PasswallBoots())
                || inv.isItemThere(new GhostAmulet())) {
            return false;
        } else {
            return !inv.isItemThere(this.key);
        }
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}