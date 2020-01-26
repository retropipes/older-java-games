/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.objects.PasswallBoots;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public abstract class GenericLock extends MazeObject {
    // Fields
    private GenericKey key;

    // Constructors
    protected GenericLock(final GenericKey mgk) {
        super(true, false);
        this.key = mgk;
    }

    protected GenericLock(final GenericKey mgk,
            final boolean doesAcceptPushInto) {
        super(true, false, doesAcceptPushInto, false, false, false, false, true,
                false);
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
        final int hash = 7;
        return 71 * hash + (this.key != null ? this.key.hashCode() : 0);
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

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        if (!inv.isItemThere(new PasswallBoots())) {
            if (!this.key.isInfinite()) {
                inv.removeItem(this.key);
            }
            app.getGameManager().decay();
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        // Handle passwall boots
        if (inv.isItemThere(new PasswallBoots())) {
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
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}