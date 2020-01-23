/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class SealedFinish extends GenericWall {
    // Constructors
    public SealedFinish() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SEALED_FINISH;
    }

    @Override
    public String getName() {
        return "Sealed Finish";
    }

    @Override
    public String getPluralName() {
        return "Sealed Finishes";
    }

    @Override
    public String getDescription() {
        return "Sealed Finishes are Finishes that are currently closed.";
    }
}