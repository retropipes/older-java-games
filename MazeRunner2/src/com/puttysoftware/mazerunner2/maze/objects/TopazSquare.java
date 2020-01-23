/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class TopazSquare extends AbstractMultipleKey {
    // Constructors
    public TopazSquare() {
        super(ColorConstants.COLOR_SKY);
    }

    @Override
    public String getName() {
        return "Topaz Square";
    }

    @Override
    public String getPluralName() {
        return "Topaz Squares";
    }

    @Override
    public String getDescription() {
        return "Topaz Squares are the keys to Topaz Walls.";
    }
}