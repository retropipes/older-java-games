/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public abstract class AbstractField extends AbstractInfiniteLock {
    // Constructors
    protected AbstractField(final AbstractPass mgp, final int tc) {
        super(mgp);
        this.setTemplateColor(tc);
    }

    protected AbstractField(final AbstractPass mgp,
            final boolean doesAcceptPushInto, final int tc) {
        super(mgp, doesAcceptPushInto);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TEXTURED;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
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
