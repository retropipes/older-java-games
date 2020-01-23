/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractToggleWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class GreenWallOff extends AbstractToggleWall {
    // Constructors
    public GreenWallOff() {
        super(false, ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Green Walls Off";
    }

    @Override
    public String getDescription() {
        return "Green Walls Off can be walked through, and will change to Green Walls On when a Green Button is pressed.";
    }
}