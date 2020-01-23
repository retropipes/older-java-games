/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericToggleWall;

public class MagentaWallOff extends GenericToggleWall {
    // Constructors
    public MagentaWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls Off";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls Off can be walked through, and will change to Magenta Walls On when a Magenta Button is pressed.";
    }
}