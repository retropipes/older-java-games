/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractPort;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class IPort extends AbstractPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_I_PORT;
    }
}