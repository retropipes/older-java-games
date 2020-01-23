/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericBarrier;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class VerticalBarrier extends GenericBarrier {
    // Constructors
    public VerticalBarrier() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_VERTICAL_BARRIER;
    }

    @Override
    public String getName() {
        return "Vertical Barrier";
    }

    @Override
    public String getPluralName() {
        return "Vertical Barriers";
    }

    @Override
    public String getDescription() {
        return "Vertical Barriers are impassable - you'll need to go around them.";
    }
}