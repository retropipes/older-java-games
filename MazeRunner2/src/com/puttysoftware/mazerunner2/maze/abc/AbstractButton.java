/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractButton extends AbstractMazeObject {
    // Fields
    private AbstractToggleWall offState;
    private AbstractToggleWall onState;

    // Constructors
    protected AbstractButton(final AbstractToggleWall off,
            final AbstractToggleWall on, final int tc) {
        super(false, false);
        this.offState = off;
        this.onState = on;
        this.setTemplateColor(tc);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BUTTON;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractButton other = (AbstractButton) obj;
        if (this.offState != other.offState
                && (this.offState == null || !this.offState
                        .equals(other.offState))) {
            return false;
        }
        if (this.onState != other.onState
                && (this.onState == null || !this.onState.equals(other.onState))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash
                + (this.offState != null ? this.offState.hashCode() : 0);
        return 13 * hash + (this.onState != null ? this.onState.hashCode() : 0);
    }

    @Override
    public AbstractButton clone() {
        AbstractButton copy = (AbstractButton) super.clone();
        copy.offState = (AbstractToggleWall) this.offState.clone();
        copy.onState = (AbstractToggleWall) this.onState.clone();
        return copy;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .findAllObjectPairsAndSwap(this.offState, this.onState);
        MazeRunnerII.getApplication().getGameManager().redrawMaze();
        SoundManager.playSound(SoundConstants.SOUND_BUTTON);
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BUTTON);
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