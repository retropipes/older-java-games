/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class SwordWall extends GenericMultipleLock {
    // Constructors
    public SwordWall() {
        super(new Sword(), ColorConstants.COLOR_SKY);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Sword Wall";
    }

    @Override
    public String getPluralName() {
        return "Sword Walls";
    }

    @Override
    public String getDescription() {
        return "Sword Walls are impassable without enough Swords.";
    }

    @Override
    public String getIdentifierV1() {
        return "Sky Crystal Wall";
    }
}