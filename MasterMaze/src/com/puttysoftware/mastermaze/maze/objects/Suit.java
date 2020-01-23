/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Suit extends GenericMultipleKey {
    // Constructors
    public Suit() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Suit";
    }

    @Override
    public String getPluralName() {
        return "Suits";
    }

    @Override
    public String getDescription() {
        return "Suits are the keys to Suit Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Magenta Crystal";
    }
}