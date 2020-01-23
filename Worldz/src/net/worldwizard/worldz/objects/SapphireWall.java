/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMultipleLock;

public class SapphireWall extends GenericMultipleLock {
    // Constructors
    public SapphireWall() {
        super(new SapphireSquare());
    }

    @Override
    public String getName() {
        return "Sapphire Wall";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Walls";
    }

    @Override
    public String getDescription() {
        return "Sapphire Walls are impassable without enough Sapphire Squares.";
    }
}