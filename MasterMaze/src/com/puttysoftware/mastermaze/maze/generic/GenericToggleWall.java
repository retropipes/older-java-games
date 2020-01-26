/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericToggleWall extends MazeObject {
    // Constructors
    protected GenericToggleWall(final boolean solidState, final int tc) {
        super(solidState, false);
        this.setTemplateColor(tc);
    }

    @Override
    public final int getBaseID() {
        if (this.isSolid()) {
            return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
        } else {
            return ObjectImageConstants.OBJECT_IMAGE_WALL_OFF;
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        MasterMaze.getApplication().showMessage("Can't go that way");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TOGGLE_WALL);
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
