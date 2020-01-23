/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleportTo;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class FinishTo extends GenericTeleportTo {
    // Constructors
    public FinishTo() {
        super(ColorConstants.COLOR_NONE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FINISH_TO;
    }

    @Override
    public String getName() {
        return "Finish To";
    }

    @Override
    public String getPluralName() {
        return "Finishes To";
    }

    @Override
    public String getDescription() {
        return "Finishes To behave like regular Finishes, except that the level they send you to might not be the next one.";
    }
}