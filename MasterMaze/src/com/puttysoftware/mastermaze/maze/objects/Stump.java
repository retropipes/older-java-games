/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Stump extends GenericWall {
    // Constructors
    public Stump() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STUMP;
    }

    @Override
    public String getName() {
        return "Stump";
    }

    @Override
    public String getPluralName() {
        return "Stumps";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Stumps stop movement, but not arrows, which pass over them unimpeded.";
    }
}