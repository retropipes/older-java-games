/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractMovableObject extends AbstractMazeObject {
    // Constructors
    protected AbstractMovableObject(final boolean pushable,
            final boolean pullable, final int attrID) {
        super(true, pushable, false, false, pullable, false, false, true,
                false);
        this.setSavedObject(new Empty());
        this.setAttributeID(attrID);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void pushAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        final Application app = MazeRunnerII.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
    }

    @Override
    public void pullAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y,
            final int pullX, final int pullY) {
        final Application app = MazeRunnerII.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MOVABLE);
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