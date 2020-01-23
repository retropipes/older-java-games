/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class MagentaWallOff extends GenericToggleWall {
    // Constructors
    public MagentaWallOff() {
        super(false, ColorConstants.COLOR_MAGENTA);
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