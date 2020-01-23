/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;

public abstract class AbstractInventoryModifier extends AbstractMazeObject {
    // Constructors
    protected AbstractInventoryModifier() {
        super(false, false);
    }

    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv);

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVENTORY_MODIFIER);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
