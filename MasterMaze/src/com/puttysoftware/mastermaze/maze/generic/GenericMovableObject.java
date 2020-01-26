/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.objects.Empty;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericMovableObject extends MazeObject {
    // Constructors
    protected GenericMovableObject(final boolean pushable,
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
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = MasterMaze.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = MasterMaze.getApplication();
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
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}