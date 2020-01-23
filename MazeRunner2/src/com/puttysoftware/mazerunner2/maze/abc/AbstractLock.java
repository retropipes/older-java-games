/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.objects.PasswallBoots;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractLock extends AbstractMazeObject {
    // Fields
    private AbstractKey key;
    protected static final long SCORE_UNLOCK = 5L;

    // Constructors
    protected AbstractLock(final AbstractKey mgk) {
        super(true, false);
        this.key = mgk;
    }

    protected AbstractLock(final AbstractKey mgk,
            final boolean doesAcceptPushInto) {
        super(true, false, doesAcceptPushInto, false, false, false, false,
                true, false);
        this.key = mgk;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractLock other = (AbstractLock) obj;
        if (this.key != other.key
                && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return 71 * hash + (this.key != null ? this.key.hashCode() : 0);
    }

    @Override
    public AbstractLock clone() {
        AbstractLock copy = (AbstractLock) super.clone();
        copy.key = this.key.clone();
        return copy;
    }

    // Accessor methods
    public AbstractKey getKey() {
        return this.key;
    }

    public void setKey(AbstractKey newKey) {
        this.key = newKey;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        if (!inv.isItemThere(new PasswallBoots())) {
            if (!this.key.isInfinite()) {
                inv.removeItem(this.key);
            }
            app.getGameManager().decay();
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
        // Handle passwall boots
        if (inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return !(inv.isItemThere(this.key));
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
    public int getCustomProperty(int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}