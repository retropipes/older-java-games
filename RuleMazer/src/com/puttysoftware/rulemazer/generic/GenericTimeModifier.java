/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.MazeConstants;

public abstract class GenericTimeModifier extends MazeObject {
    // Constructors
    protected GenericTimeModifier() {
        super(false);
    }

    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv);

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TIME_MODIFIER);
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
