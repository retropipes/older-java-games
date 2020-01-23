/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class OrangeWallOn extends GenericToggleWall {
    // Constructors
    public OrangeWallOn() {
        super(true, ColorConstants.COLOR_ORANGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall On";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls On";
    }

    @Override
    public String getDescription() {
        return "Orange Walls On can NOT be walked through, and will change to Orange Walls Off when a Orange Button is pressed.";
    }
}