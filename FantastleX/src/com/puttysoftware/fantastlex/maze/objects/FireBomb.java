/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBomb;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class FireBomb extends AbstractBomb {
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
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanEnrageObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Burn the ground, too
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanBurnGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
