/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericTrappedWall extends GenericWall {
    // Fields
    private int number;
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericTrappedWall(final int newNumber) {
        super();
        this.number = newNumber;
        this.setType(TypeConstants.TYPE_TRAPPED_WALL);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Wall");
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public GenericTrappedWall clone() {
        final GenericTrappedWall copy = (GenericTrappedWall) super.clone();
        copy.number = this.number;
        return copy;
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
    public String getPluralName() {
        if (this.number == GenericTrappedWall.NUMBER_MASTER) {
            return "Master Trapped Walls";
        } else {
            return "Trapped Walls " + this.number;
        }
    }
}