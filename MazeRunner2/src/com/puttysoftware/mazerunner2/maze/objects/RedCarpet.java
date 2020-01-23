/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractCarpet;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class RedCarpet extends AbstractCarpet {
    // Constructors
    public RedCarpet() {
        super("Red", ColorConstants.COLOR_RED);
    }
}