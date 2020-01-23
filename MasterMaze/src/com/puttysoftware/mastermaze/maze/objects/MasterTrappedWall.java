/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericTrappedWall;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MasterTrappedWall extends GenericTrappedWall {
    public MasterTrappedWall() {
        super(GenericTrappedWall.NUMBER_MASTER);
    }

    @Override
    public String getDescription() {
        return "Master Trapped Walls disappear when any Wall Trap is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_MASTER;
    }
}
