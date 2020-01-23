/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractPort;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class LPort extends AbstractPort {
    // Constructors
    public LPort() {
        super(new LPlug(), 'L');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_L_PORT;
    }
}