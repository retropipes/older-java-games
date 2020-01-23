/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericPlug;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class GPlug extends GenericPlug {
    // Constructors
    public GPlug() {
        super('G');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_G_PLUG;
    }
}