/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class PoisonBomb extends GenericBomb {
    // Constructors
    public PoisonBomb() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Poison Bomb";
    }

    @Override
    public String getPluralName() {
        return "Poison Bombs";
    }

    @Override
    public String getDescription() {
        return "Poison Bombs poison anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Poison objects that react to poison
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanPoisonObjects(x, y, z, GenericBomb.EFFECT_RADIUS);
        // Poison the ground, too
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanPoisonGround(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}
