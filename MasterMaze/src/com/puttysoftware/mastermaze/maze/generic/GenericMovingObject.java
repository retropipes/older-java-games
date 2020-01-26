package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public abstract class GenericMovingObject extends MazeObject {
    // Constructors
    public GenericMovingObject(final boolean solid) {
        super(solid, false);
    }

    public GenericMovingObject(final boolean solid, final int tc,
            final int attr, final int attrColor) {
        super(solid, true);
        this.setTemplateColor(tc);
        this.setAttributeID(attr);
        this.setAttributeTemplateColor(attrColor);
    }

    // Methods
    @Override
    public boolean isMoving() {
        return true;
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
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
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
