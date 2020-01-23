/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class PurpleWallOff extends GenericToggleWall {
    // Constructors
    public PurpleWallOff() {
        super(false, ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Purple Walls Off";
    }

    @Override
    public String getDescription() {
        return "Purple Walls Off can be walked through, and will change to Purple Walls On when a Purple Button is pressed.";
    }
}