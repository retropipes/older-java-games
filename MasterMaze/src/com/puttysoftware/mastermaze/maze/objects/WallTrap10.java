/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericWallTrap;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class WallTrap10 extends GenericWallTrap {
    public WallTrap10() {
        super(10, new TrappedWall10());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 10 disappear when stepped on, causing all Trapped Walls 10 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_10;
    }
}
