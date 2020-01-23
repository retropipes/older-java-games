/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public abstract class GenericTrappedWall extends GenericWall {
    // Fields
    private int number;
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericTrappedWall(final int newNumber) {
        super(ColorConstants.COLOR_BROWN);
        this.number = newNumber;
    }

    @Override
    public GenericTrappedWall clone() {
        final GenericTrappedWall copy = (GenericTrappedWall) super.clone();
        copy.number = this.number;
        return copy;
    }

    @Override
    public abstract int getAttributeID();

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public String getName() {
        if (this.number == GenericTrappedWall.NUMBER_MASTER) {
            return "Master Trapped Wall";
        } else {
            return "Trapped Wall " + this.number;
        }
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        if (this.number == GenericTrappedWall.NUMBER_MASTER) {
            return "Master Trapped Walls";
        } else {
            return "Trapped Walls " + this.number;
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRAPPED_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}