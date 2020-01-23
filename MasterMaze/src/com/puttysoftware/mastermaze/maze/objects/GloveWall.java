/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class GloveWall extends GenericMultipleLock {
    // Constructors
    public GloveWall() {
        super(new Glove(), ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove Wall";
    }

    @Override
    public String getPluralName() {
        return "Glove Walls";
    }

    @Override
    public String getDescription() {
        return "Glove Walls are impassable without enough Gloves.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal Wall";
    }
}