/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class BoltWall extends GenericMultipleLock {
    // Constructors
    public BoltWall() {
        super(new Bolt(), ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt Wall";
    }

    @Override
    public String getPluralName() {
        return "Bolt Walls";
    }

    @Override
    public String getDescription() {
        return "Bolt Walls are impassable without enough Bolts.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal Wall";
    }
}