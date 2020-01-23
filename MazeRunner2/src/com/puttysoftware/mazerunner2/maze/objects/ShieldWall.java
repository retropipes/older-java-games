/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class ShieldWall extends AbstractMultipleLock {
    // Constructors
    public ShieldWall() {
        super(new Shield(), ColorConstants.COLOR_ORANGE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SHIELD;
    }

    @Override
    public String getName() {
        return "Shield Wall";
    }

    @Override
    public String getPluralName() {
        return "Shield Walls";
    }

    @Override
    public String getDescription() {
        return "Shield Walls are impassable without enough Shields.";
    }

    @Override
    public String getIdentifierV1() {
        return "Orange Crystal Wall";
    }
}