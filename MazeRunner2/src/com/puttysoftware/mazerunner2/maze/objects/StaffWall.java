/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class StaffWall extends AbstractMultipleLock {
    // Constructors
    public StaffWall() {
        super(new Staff(), ColorConstants.COLOR_PURPLE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAFF;
    }

    @Override
    public String getName() {
        return "Staff Wall";
    }

    @Override
    public String getPluralName() {
        return "Staff Walls";
    }

    @Override
    public String getDescription() {
        return "Staff Walls are impassable without enough Staves.";
    }

    @Override
    public String getIdentifierV1() {
        return "Purple Crystal Wall";
    }
}