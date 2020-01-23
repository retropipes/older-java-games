/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class ExploreWall extends GenericMultipleLock {
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