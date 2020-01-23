/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Sword extends GenericMultipleKey {
    // Constructors
    public Sword() {
        super(ColorConstants.COLOR_SKY);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SWORD;
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public String getPluralName() {
        return "Swords";
    }

    @Override
    public String getDescription() {
        return "Swords are the keys to Sword Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Sky Crystal";
    }
}