/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Necklace extends GenericMultipleKey {
    // Constructors
    public Necklace() {
        super(ColorConstants.COLOR_ROSE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NECKLACE;
    }

    @Override
    public String getName() {
        return "Necklace";
    }

    @Override
    public String getPluralName() {
        return "Necklaces";
    }

    @Override
    public String getDescription() {
        return "Necklaces are the keys to Necklace Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Rose Crystal";
    }
}