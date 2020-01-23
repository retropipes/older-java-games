/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Bolt extends GenericMultipleKey {
    // Constructors
    public Bolt() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt";
    }

    @Override
    public String getPluralName() {
        return "Bolts";
    }

    @Override
    public String getDescription() {
        return "Bolts are the keys to Bolt Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal";
    }
}