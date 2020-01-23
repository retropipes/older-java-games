/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;

public abstract class GenericGround extends MazeObject {
    // Constructors
    protected GenericGround() {
        super();
        this.setType(TypeConstants.TYPE_GROUND);
    }

    protected GenericGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut) {
        super();
        this.setPushableInto(doesAcceptPushInto);
        this.setPushableOut(doesAcceptPushOut);
        this.setPullableInto(doesAcceptPullInto);
        this.setPullableOut(doesAcceptPullOut);
        this.setType(TypeConstants.TYPE_GROUND);
    }

    protected GenericGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction) {
        super();
        this.setPushableInto(doesAcceptPushInto);
        this.setPushableOut(doesAcceptPushOut);
        this.setPullableInto(doesAcceptPullInto);
        this.setPullableOut(doesAcceptPullOut);
        this.setFriction(hasFriction);
        this.setType(TypeConstants.TYPE_GROUND);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_GROUND;
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
