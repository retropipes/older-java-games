/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericTrappedWall;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class TrappedWall17 extends GenericTrappedWall {
    public TrappedWall17() {
        super(17);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 17 disappear when any Wall Trap 17 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_17;
    }
}
