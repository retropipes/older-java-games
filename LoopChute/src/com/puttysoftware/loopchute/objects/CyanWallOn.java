/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericToggleWall;

public class CyanWallOn extends GenericToggleWall {
    // Constructors
    public CyanWallOn() {
        super(true, ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Wall On";
    }

    @Override
    public String getPluralName() {
        return "Cyan Walls On";
    }

    @Override
    public String getDescription() {
        return "Cyan Walls On can NOT be walked through, and will change to Cyan Walls Off when a Cyan Button is pressed.";
    }
}