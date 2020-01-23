/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Explore extends GenericMultipleKey {
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