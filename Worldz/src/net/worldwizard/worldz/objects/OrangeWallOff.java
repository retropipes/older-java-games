/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericToggleWall;

public class OrangeWallOff extends GenericToggleWall {
    // Constructors
    public OrangeWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls Off";
    }

    @Override
    public String getDescription() {
        return "Orange Walls Off can be walked through, and will change to Orange Walls On when a Orange Button is pressed.";
    }
}