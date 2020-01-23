/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractPlug;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class GPlug extends AbstractPlug {
    // Constructors
    public GPlug() {
        super('G');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_G_PLUG;
    }
}