/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericGround;

public class Sand extends GenericGround {
    // Constructors
    public Sand() {
        super();
    }

    @Override
    public String getName() {
        return "Sand";
    }

    @Override
    public String getPluralName() {
        return "Squares of Sand";
    }

    @Override
    public String getDescription() {
        return "Sand is one of the many types of ground.";
    }
}