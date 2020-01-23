/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractToggleWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class MagentaWallOn extends AbstractToggleWall {
    // Constructors
    public MagentaWallOn() {
        super(true, ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall On";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls On";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls On can NOT be walked through, and will change to Magenta Walls Off when a Magenta Button is pressed.";
    }
}