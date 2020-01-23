/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericTrappedWall;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class TrappedWall13 extends GenericTrappedWall {
    public TrappedWall13() {
        super(13);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 13 disappear when any Wall Trap 13 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_13;
    }
}
