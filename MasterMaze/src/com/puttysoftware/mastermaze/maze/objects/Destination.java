/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericTeleport;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Destination extends GenericTeleport {
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
    public MazeObject editorPropertiesHook() {
        return this;
    }

    @Override
    public String getDescription() {
        return "";
    }
}