/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;

public class GarnetSquare extends GenericMultipleKey {
    // Constructors
    public GarnetSquare() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Garnet Square";
    }

    @Override
    public String getPluralName() {
        return "Garnet Squares";
    }

    @Override
    public String getDescription() {
        return "Garnet Squares are the keys to Garnet Walls.";
    }
}