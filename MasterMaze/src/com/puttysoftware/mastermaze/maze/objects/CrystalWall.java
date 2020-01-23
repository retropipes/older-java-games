/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class CrystalWall extends GenericProgrammableLock {
    // Constructors
    public CrystalWall() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CRYSTAL_WALL;
    }

    @Override
    public String getName() {
        return "Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Crystal Walls require one Crystal to open. The crystal type required may be different from wall to wall.";
    }
}