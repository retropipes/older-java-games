/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractPort;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class PPort extends AbstractPort {
    // Constructors
    public PPort() {
        super(new PPlug(), 'P');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_P_PORT;
    }
}