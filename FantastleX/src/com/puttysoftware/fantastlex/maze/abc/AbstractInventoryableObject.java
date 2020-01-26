/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractInventoryableObject extends AbstractMazeObject {
    // Constructors
    protected AbstractInventoryableObject() {
        super(false, true, false);
    }

    protected AbstractInventoryableObject(final boolean isUsable,
            final int newUses) {
        super(false, isUsable, newUses, true, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        inv.addItem(this);
        final Application app = FantastleX.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
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
