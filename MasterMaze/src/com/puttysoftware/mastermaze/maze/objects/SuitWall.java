/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class SuitWall extends GenericMultipleLock {
    // Constructors
    public SuitWall() {
        super(new Suit(), ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Suit Wall";
    }

    @Override
    public String getPluralName() {
        return "Suit Walls";
    }

    @Override
    public String getDescription() {
        return "Suit Walls are impassable without enough Suits.";
    }

    @Override
    public String getIdentifierV1() {
        return "Magenta Crystal Wall";
    }
}