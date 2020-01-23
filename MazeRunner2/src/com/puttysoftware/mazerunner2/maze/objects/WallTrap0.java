/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractWallTrap;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class WallTrap0 extends AbstractWallTrap {
    public WallTrap0() {
        super(0, new TrappedWall0());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 0 disappear when stepped on, causing all Trapped Walls 0 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_0;
    }
}
