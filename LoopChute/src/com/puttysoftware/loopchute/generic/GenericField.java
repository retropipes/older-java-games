/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.maze.MazeConstants;

public abstract class GenericField extends GenericInfiniteLock {
    // Constructors
    protected GenericField(final GenericPass mgp, final int tc) {
        super(mgp);
        this.setTemplateColor(tc);
    }

    protected GenericField(final GenericPass mgp,
            final boolean doesAcceptPushInto, final int tc) {
        super(mgp, doesAcceptPushInto);
        this.setTemplateColor(tc);
    }

    @Override
    public String getBaseName() {
        return "textured";
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
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_FIELD);
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}
