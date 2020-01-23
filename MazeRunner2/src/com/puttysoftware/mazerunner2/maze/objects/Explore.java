/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Explore extends AbstractMultipleKey {
    // Constructors
    public Explore() {
        super(ColorConstants.COLOR_SEAWEED);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXPLORE;
    }

    @Override
    public String getName() {
        return "Explore";
    }

    @Override
    public String getPluralName() {
        return "Explores";
    }

    @Override
    public String getDescription() {
        return "Explores are the keys to Explore Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Seaweed Crystal";
    }
}