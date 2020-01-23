/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.DirectionConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public abstract class AbstractTransientObject extends AbstractMazeObject {
    // Fields
    private final String name;
    private int dir;

    // Constructors
    protected AbstractTransientObject(final String newName, final int arrowColor) {
        super(true, false);
        this.name = newName;
        this.dir = DirectionConstants.DIRECTION_NONE;
        this.setTemplateColor(arrowColor);
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public final int getBaseID() {
        switch (this.dir) {
        case DirectionConstants.DIRECTION_NORTH:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_NORTH;
        case DirectionConstants.DIRECTION_NORTHEAST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_NORTHEAST;
        case DirectionConstants.DIRECTION_EAST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_EAST;
        case DirectionConstants.DIRECTION_SOUTHEAST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_SOUTHEAST;
        case DirectionConstants.DIRECTION_SOUTH:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_SOUTH;
        case DirectionConstants.DIRECTION_SOUTHWEST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_SOUTHWEST;
        case DirectionConstants.DIRECTION_WEST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_WEST;
        case DirectionConstants.DIRECTION_NORTHWEST:
            return ObjectImageConstants.OBJECT_IMAGE_ARROW_NORTHWEST;
        default:
            return ObjectImageConstants.OBJECT_IMAGE_EMPTY;
        }
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public String getPluralName() {
        return this.name + "s";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public final void setDirection(final int newDir) {
        this.dir = newDir;
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected final void setTypes() {
        // Do nothing
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
