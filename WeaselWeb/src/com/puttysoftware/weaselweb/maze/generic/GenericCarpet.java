/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericCarpet extends GenericGround {
    // Fields
    private final String color;

    // Constructors
    protected GenericCarpet(final String newColor) {
        super();
        this.color = newColor;
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
