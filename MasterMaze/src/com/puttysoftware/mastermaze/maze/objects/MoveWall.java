/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MoveWall extends GenericMultipleLock {
    // Constructors
    public MoveWall() {
        super(new Move(), ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_MOVE;
    }

    @Override
    public String getName() {
        return "Move Wall";
    }

    @Override
    public String getPluralName() {
        return "Move Walls";
    }

    @Override
    public String getDescription() {
        return "Move Walls are impassable without enough Moves.";
    }

    @Override
    public String getIdentifierV1() {
        return "White Crystal Wall";
    }
}