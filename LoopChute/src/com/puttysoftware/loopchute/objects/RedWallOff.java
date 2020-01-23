/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericToggleWall;

public class RedWallOff extends GenericToggleWall {
    // Constructors
    public RedWallOff() {
        super(false, ColorConstants.COLOR_RED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Red Walls Off";
    }

    @Override
    public String getDescription() {
        return "Red Walls Off can be walked through, and will change to Red Walls On when a Red Button is pressed.";
    }
}