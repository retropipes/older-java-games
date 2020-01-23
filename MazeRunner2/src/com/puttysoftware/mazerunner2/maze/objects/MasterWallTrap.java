/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractWallTrap;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class MasterWallTrap extends AbstractWallTrap {
    public MasterWallTrap() {
        super(AbstractWallTrap.NUMBER_MASTER, null);
    }

    @Override
    public String getDescription() {
        return "Master Wall Traps disappear when stepped on, causing all types of Trapped Walls to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_MASTER;
    }
}
