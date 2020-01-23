/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class BlueWallOff extends GenericToggleWall {
    // Constructors
    public BlueWallOff() {
        super(false, ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls Off";
    }

    @Override
    public String getDescription() {
        return "Blue Walls Off can be walked through, and will change to Blue Walls On when a Blue Button is pressed.";
    }
}