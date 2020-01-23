/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.generic;

import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.maze.MazeConstants;

public abstract class GenericGround extends MazeObject {
    // Constructors
    protected GenericGround() {
        super(false);
    }

    protected GenericGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut) {
        super(false, false, doesAcceptPushInto, doesAcceptPushOut, false,
                doesAcceptPullInto, doesAcceptPullOut, true, false, 0, false,
                false);
    }

    protected GenericGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction) {
        super(false, false, doesAcceptPushInto, doesAcceptPushOut, false,
                doesAcceptPullInto, doesAcceptPullOut, hasFriction, false, 0,
                false, false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GROUND);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Do nothing
    }
}
