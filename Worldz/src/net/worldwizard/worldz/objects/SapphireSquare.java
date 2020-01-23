/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMultipleKey;

public class SapphireSquare extends GenericMultipleKey {
    // Constructors
    public SapphireSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Sapphire Square";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Squares";
    }

    @Override
    public String getDescription() {
        return "Sapphire Squares are the keys to Sapphire Walls.";
    }
}