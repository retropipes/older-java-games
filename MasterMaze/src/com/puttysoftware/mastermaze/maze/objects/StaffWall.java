/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class StaffWall extends GenericMultipleLock {
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