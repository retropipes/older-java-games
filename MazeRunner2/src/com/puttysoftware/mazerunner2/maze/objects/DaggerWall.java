/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class DaggerWall extends AbstractMultipleLock {
    // Constructors
    public DaggerWall() {
        super(new Dagger(), ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger Wall";
    }

    @Override
    public String getPluralName() {
        return "Dagger Walls";
    }

    @Override
    public String getDescription() {
        return "Dagger Walls are impassable without enough Daggers.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal Wall";
    }
}