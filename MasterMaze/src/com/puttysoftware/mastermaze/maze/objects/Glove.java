/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Glove extends GenericMultipleKey {
    // Constructors
    public Glove() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove";
    }

    @Override
    public String getPluralName() {
        return "Gloves";
    }

    @Override
    public String getDescription() {
        return "Gloves are the keys to Glove Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal";
    }
}