/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMultipleLock;

public class TopazWall extends GenericMultipleLock {
    // Constructors
    public TopazWall() {
        super(new TopazSquare());
    }

    @Override
    public String getName() {
        return "Topaz Wall";
    }

    @Override
    public String getPluralName() {
        return "Topaz Walls";
    }

    @Override
    public String getDescription() {
        return "Topaz Walls are impassable without enough Topaz Squares.";
    }
}