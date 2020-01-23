/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class GreenWallOn extends GenericToggleWall {
    // Constructors
    public GreenWallOn() {
        super(true, ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall On";
    }

    @Override
    public String getPluralName() {
        return "Green Walls On";
    }

    @Override
    public String getDescription() {
        return "Green Walls On can NOT be walked through, and will change to Green Walls Off when a Green Button is pressed.";
    }
}