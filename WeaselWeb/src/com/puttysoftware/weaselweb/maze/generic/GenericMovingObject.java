package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;

public abstract class GenericMovingObject extends MazeObject {
    // Fields
    protected MazeObject savedObject;

    // Constructors
    public GenericMovingObject(final boolean solid) {
        super();
        this.getSolidProperties().setSolid(solid);
        this.setType(TypeConstants.TYPE_DUNGEON);
    }

    // Methods
    @Override
    public boolean isMoving() {
        return true;
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.postMoveActionHook();
    }

    public void postMoveActionHook() {
        // Do nothing
    }

    @Override
    public abstract String getName();

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
