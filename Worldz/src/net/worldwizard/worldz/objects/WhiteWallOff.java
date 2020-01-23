/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericToggleWall;

public class WhiteWallOff extends GenericToggleWall {
    // Constructors
    public WhiteWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Wall Off";
    }

    @Override
    public String getPluralName() {
        return "White Walls Off";
    }

    @Override
    public String getDescription() {
        return "White Walls Off can be walked through, and will change to White Walls On when a White Button is pressed.";
    }
}