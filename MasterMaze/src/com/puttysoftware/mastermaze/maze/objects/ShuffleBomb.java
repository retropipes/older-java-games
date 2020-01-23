/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class ShuffleBomb extends GenericBomb {
    // Constructors
    public ShuffleBomb() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public String getName() {
        return "Shuffle Bomb";
    }

    @Override
    public String getPluralName() {
        return "Shuffle Bombs";
    }

    @Override
    public String getDescription() {
        return "Shuffle Bombs randomly rearrange anything in an area of radius 3 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Shuffle objects
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanShuffleObjects(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}