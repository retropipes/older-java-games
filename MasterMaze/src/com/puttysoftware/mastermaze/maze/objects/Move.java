/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Move extends GenericMultipleKey {
    // Constructors
    public Move() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_MOVE;
    }

    @Override
    public String getName() {
        return "Move";
    }

    @Override
    public String getPluralName() {
        return "Moves";
    }

    @Override
    public String getDescription() {
        return "Moves are the keys to Move Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "White Crystal";
    }
}