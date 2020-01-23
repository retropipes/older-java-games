/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMultipleKey;

public class SilverSquare extends GenericMultipleKey {
    // Constructors
    public SilverSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Silver Square";
    }

    @Override
    public String getPluralName() {
        return "Silver Squares";
    }

    @Override
    public String getDescription() {
        return "Silver Squares are the keys to Silver Walls.";
    }
}