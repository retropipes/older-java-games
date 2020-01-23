/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Glove extends AbstractMultipleKey {
    // Constructors
    public Glove() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove";
    }

    @Override
    public String getPluralName() {
        return "Gloves";
    }

    @Override
    public String getDescription() {
        return "Gloves are the keys to Glove Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal";
    }
}