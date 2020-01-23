/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWall;

public class OneWayNorthWall extends GenericWall {
    public OneWayNorthWall() {
        super(false, true, true, true, false, true, true, true);
    }

    @Override
    public String getName() {
        return "One-Way North Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way North Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way North Walls allow movement through them only North.";
    }
}
