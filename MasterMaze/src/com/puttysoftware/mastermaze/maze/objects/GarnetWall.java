/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;

public class GarnetWall extends GenericMultipleLock {
    // Constructors
    public GarnetWall() {
        super(new GarnetSquare(), ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Garnet Wall";
    }

    @Override
    public String getPluralName() {
        return "Garnet Walls";
    }

    @Override
    public String getDescription() {
        return "Garnet Walls are impassable without enough Garnet Squares.";
    }
}