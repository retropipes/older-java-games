/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class ShockBomb extends GenericBomb {
    // Constructors
    public ShockBomb() {
        super(ColorConstants.COLOR_LIGHT_YELLOW);
    }

    @Override
    public String getName() {
        return "Shock Bomb";
    }

    @Override
    public String getPluralName() {
        return "Shock Bombs";
    }

    @Override
    public String getDescription() {
        return "Shock Bombs shock anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Shock objects that react to shock
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanShockObjects(x, y, z, GenericBomb.EFFECT_RADIUS);
        // Shock the ground, too
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanShockGround(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}
