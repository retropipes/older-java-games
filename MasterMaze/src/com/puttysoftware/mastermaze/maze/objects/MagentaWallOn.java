/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

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