/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Hammer extends AbstractSingleKey {
    // Constructors
    public Hammer() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HAMMER;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public String getPluralName() {
        return "Hammers";
    }

    @Override
    public String getDescription() {
        return "Hammers are used to destroy Brick Walls, and can only be used once.";
    }
}