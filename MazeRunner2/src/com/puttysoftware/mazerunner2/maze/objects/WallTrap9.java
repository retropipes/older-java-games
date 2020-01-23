/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractWallTrap;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class WallTrap9 extends AbstractWallTrap {
    public WallTrap9() {
        super(9, new TrappedWall9());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 9 disappear when stepped on, causing all Trapped Walls 9 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_9;
    }
}
