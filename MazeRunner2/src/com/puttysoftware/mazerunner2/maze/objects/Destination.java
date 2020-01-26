/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTeleport;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Destination extends AbstractTeleport {
    // Constructors
    public Destination() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_DESTINATION);
    }

    @Override
    public String getName() {
        return "Destination";
    }

    @Override
    public String getPluralName() {
        return "Destinations";
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        return this;
    }

    @Override
    public String getDescription() {
        return "";
    }
}