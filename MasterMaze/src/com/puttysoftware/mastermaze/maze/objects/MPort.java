/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.GenericPort;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MPort extends GenericPort {
    // Constructors
    public MPort() {
        super(new MPlug(), 'M');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_M_PORT;
    }
}