/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class NecklaceWall extends GenericMultipleLock {
    // Constructors
    public NecklaceWall() {
        super(new Necklace(), ColorConstants.COLOR_ROSE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NECKLACE;
    }

    @Override
    public String getName() {
        return "Necklace Wall";
    }

    @Override
    public String getPluralName() {
        return "Necklace Walls";
    }

    @Override
    public String getDescription() {
        return "Necklace Walls are impassable without enough Necklaces.";
    }

    @Override
    public String getIdentifierV1() {
        return "Rose Crystal Wall";
    }
}