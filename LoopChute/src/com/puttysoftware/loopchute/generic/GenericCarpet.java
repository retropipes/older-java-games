/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

public abstract class GenericCarpet extends GenericGround {
    // Fields
    private final String color;

    // Constructors
    protected GenericCarpet(final String newColor, final int newTC) {
        super(newTC);
        this.color = newColor;
    }

    @Override
    public final String getBaseName() {
        return "carpet";
    }

    @Override
    public final String getName() {
        return this.color + " Carpet";
    }

    @Override
    public final String getPluralName() {
        return "Squares of " + this.color + " Carpet";
    }

    @Override
    public final String getDescription() {
        return "Squares of " + this.color
                + " Carpet are one of the many types of ground.";
    }
}
