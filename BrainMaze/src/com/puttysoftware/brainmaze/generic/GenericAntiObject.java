/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public abstract class GenericAntiObject extends MazeObject {
    // Constructors
    protected GenericAntiObject() {
        super(false, false, true, true, false, true, true, false, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_ANTI_OBJECT);
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
