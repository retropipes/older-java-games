/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericToggleWall;

public class YellowWallOn extends GenericToggleWall {
    // Constructors
    public YellowWallOn() {
        super(true, ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall On";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls On";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls On can NOT be walked through, and will change to Yellow Walls Off when a Yellow Button is pressed.";
    }
}