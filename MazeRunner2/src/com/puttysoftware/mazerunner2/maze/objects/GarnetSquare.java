/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class GarnetSquare extends AbstractMultipleKey {
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