/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class FireBomb extends GenericBomb {
    // Constructors
    public FireBomb() {
        super(ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "Fire Bomb";
    }

    @Override
    public String getPluralName() {
        return "Fire Bombs";
    }

    @Override
    public String getDescription() {
        return "Fire Bombs burn anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Enrage objects that react to fire
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanEnrageObjects(x, y, z, GenericBomb.EFFECT_RADIUS);
        // Burn the ground, too
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanBurnGround(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}
