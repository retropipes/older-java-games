/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public abstract class GenericTimeModifier extends MazeObject {
    // Constructors
    protected GenericTimeModifier(final int attr, final int attrColor) {
        super(false, false);
        this.setAttributeID(attr);
        this.setAttributeTemplateColor(attrColor);
    }

    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv);

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HOURGLASS;
    }

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
