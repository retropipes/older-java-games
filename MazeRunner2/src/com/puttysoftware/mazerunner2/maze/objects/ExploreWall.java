/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class ExploreWall extends AbstractMultipleLock {
    // Constructors
    public ExploreWall() {
        super(new Explore(), ColorConstants.COLOR_SEAWEED);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXPLORE;
    }

    @Override
    public String getName() {
        return "Explore Wall";
    }

    @Override
    public String getPluralName() {
        return "Explore Walls";
    }

    @Override
    public String getDescription() {
        return "Explore Walls are impassable without enough Explores.";
    }

    @Override
    public String getIdentifierV1() {
        return "Seaweed Crystal Wall";
    }
}