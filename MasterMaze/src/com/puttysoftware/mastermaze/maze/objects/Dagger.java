/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Dagger extends GenericMultipleKey {
    // Constructors
    public Dagger() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger";
    }

    @Override
    public String getPluralName() {
        return "Daggers";
    }

    @Override
    public String getDescription() {
        return "Daggers are the keys to Dagger Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal";
    }
}