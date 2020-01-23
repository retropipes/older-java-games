/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractWallTrap;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class WallTrap13 extends AbstractWallTrap {
    public WallTrap13() {
        super(13, new TrappedWall13());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 13 disappear when stepped on, causing all Trapped Walls 13 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_13;
    }
}
