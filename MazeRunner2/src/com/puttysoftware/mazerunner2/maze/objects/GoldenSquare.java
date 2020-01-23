/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class GoldenSquare extends AbstractMultipleKey {
    // Constructors
    public GoldenSquare() {
        super(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Golden Square";
    }

    @Override
    public String getPluralName() {
        return "Golden Squares";
    }

    @Override
    public String getDescription() {
        return "Golden Squares are the keys to Golden Walls.";
    }
}