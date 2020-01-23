/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class MoveWall extends AbstractMultipleLock {
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