/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractToggleWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class CyanWallOn extends AbstractToggleWall {
    // Constructors
    public CyanWallOn() {
        super(true, ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Wall On";
    }

    @Override
    public String getPluralName() {
        return "Cyan Walls On";
    }

    @Override
    public String getDescription() {
        return "Cyan Walls On can NOT be walked through, and will change to Cyan Walls Off when a Cyan Button is pressed.";
    }
}