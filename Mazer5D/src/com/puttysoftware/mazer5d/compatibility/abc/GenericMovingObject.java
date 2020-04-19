package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.objectmodel.Layers;

public abstract class GenericMovingObject extends MazeObjectModel {
    // Fields
    protected MazeObjectModel savedObject;

    // Constructors
    public GenericMovingObject(final boolean solid) {
        super(solid);
    }

    // Methods
    @Override
    public boolean isMoving() {
        return true;
    }

    public MazeObjectModel getSavedObject() {
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
        return Layers.OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_DUNGEON);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    public void setSavedObject(MazeObjectModel inThere) {
        // Do nothing
    }
}
