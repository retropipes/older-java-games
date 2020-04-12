package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.compatibility.maze.MazeConstants;
import com.puttysoftware.mazer5d.game.ObjectInventory;

public abstract class GenericMovingObject extends MazeObject {
    // Fields
    protected MazeObject savedObject;

    // Constructors
    public GenericMovingObject(final boolean solid) {
        super(solid);
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
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_DUNGEON);
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
