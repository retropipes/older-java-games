/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericToggleWall;

public class SeaweedWallOn extends GenericToggleWall {
    // Constructors
    public SeaweedWallOn() {
        super(true, ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall On";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls On";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls On can NOT be walked through, and will change to Seaweed Walls Off when a Seaweed Button is pressed.";
    }
}