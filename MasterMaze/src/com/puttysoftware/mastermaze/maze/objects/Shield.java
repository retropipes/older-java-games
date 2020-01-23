/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Shield extends GenericMultipleKey {
    // Constructors
    public Shield() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SHIELD;
    }

    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public String getPluralName() {
        return "Shields";
    }

    @Override
    public String getDescription() {
        return "Shields are the keys to Shield Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Orange Crystal";
    }
}