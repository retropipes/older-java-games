/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;

public abstract class GenericField extends GenericInfiniteLock {
    // Constructors
    protected GenericField(final GenericPass mgp) {
        super(mgp);
        this.setType(TypeConstants.TYPE_FIELD);
    }

    protected GenericField(final GenericPass mgp,
            final boolean doesAcceptPushInto) {
        super(mgp, doesAcceptPushInto);
        this.setType(TypeConstants.TYPE_FIELD);
    }

    protected GenericField(final boolean isSolid, final GenericPass mgp) {
        super(isSolid, mgp);
        this.setType(TypeConstants.TYPE_FIELD);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_GROUND;
    }
}
