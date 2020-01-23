/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class SapphireSquare extends AbstractMultipleKey {
    // Constructors
    public SapphireSquare() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Sapphire Square";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Squares";
    }

    @Override
    public String getDescription() {
        return "Sapphire Squares are the keys to Sapphire Walls.";
    }
}