/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

public abstract class AbstractCarpet extends AbstractGround {
    // Fields
    private String color;

    // Constructors
    protected AbstractCarpet(String newColor, int tc) {
        super(tc);
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
