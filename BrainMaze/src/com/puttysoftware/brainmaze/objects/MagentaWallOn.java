/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericToggleWall;

public class MagentaWallOn extends GenericToggleWall {
    // Constructors
    public MagentaWallOn() {
        super(true, ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall On";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls On";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls On can NOT be walked through, and will change to Magenta Walls Off when a Magenta Button is pressed.";
    }
}