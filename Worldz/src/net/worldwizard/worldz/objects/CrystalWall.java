/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericProgrammableLock;

public class CrystalWall extends GenericProgrammableLock {
    // Constructors
    public CrystalWall() {
        super();
    }

    @Override
    public String getName() {
        return "Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Crystal Walls require one Crystal to open. The crystal type required may be different from wall to wall.";
    }
}