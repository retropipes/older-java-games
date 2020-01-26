/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericPassThroughObject extends MazeObject {
    // Constructors
    protected GenericPassThroughObject() {
        super(false, false);
    }

    protected GenericPassThroughObject(final boolean sightBlock) {
        super(false, sightBlock);
    }

    protected GenericPassThroughObject(final boolean acceptPushInto,
            final boolean acceptPushOut, final boolean acceptPullInto,
            final boolean acceptPullOut) {
        super(false, false, acceptPushInto, acceptPushOut, false,
                acceptPullInto, acceptPullOut, true, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
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