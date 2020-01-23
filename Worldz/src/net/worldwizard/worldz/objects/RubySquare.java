/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMultipleKey;

public class RubySquare extends GenericMultipleKey {
    // Constructors
    public RubySquare() {
        super();
    }

    @Override
    public String getName() {
        return "Ruby Square";
    }

    @Override
    public String getPluralName() {
        return "Ruby Squares";
    }

    @Override
    public String getDescription() {
        return "Ruby Squares are the keys to Ruby Walls.";
    }
}