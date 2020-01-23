/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractTrappedWall;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class TrappedWall16 extends AbstractTrappedWall {
    public TrappedWall16() {
        super(16);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 16 disappear when any Wall Trap 16 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_16;
    }
}
