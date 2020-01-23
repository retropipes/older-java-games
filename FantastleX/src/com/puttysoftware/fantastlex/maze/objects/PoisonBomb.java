/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBomb;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class PoisonBomb extends AbstractBomb {
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
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanPoisonObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Poison the ground, too
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanPoisonGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
