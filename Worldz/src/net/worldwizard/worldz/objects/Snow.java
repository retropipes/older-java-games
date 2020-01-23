/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericGround;

public class Snow extends GenericGround {
    // Constructors
    public Snow() {
        super();
    }

    @Override
    public String getName() {
        return "Snow";
    }

    @Override
    public String getPluralName() {
        return "Squares of Snow";
    }

    @Override
    public String getDescription() {
        return "Snow is one of the many types of ground.";
    }
}