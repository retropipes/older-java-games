/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class CapWall extends GenericMultipleLock {
    // Constructors
    public CapWall() {
        super(new Cap(), ColorConstants.COLOR_RED);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_CAP;
    }

    @Override
    public String getName() {
        return "Cap Wall";
    }

    @Override
    public String getPluralName() {
        return "Cap Walls";
    }

    @Override
    public String getDescription() {
        return "Cap Walls are impassable without enough Caps.";
    }

    @Override
    public String getIdentifierV1() {
        return "Red Crystal Wall";
    }
}