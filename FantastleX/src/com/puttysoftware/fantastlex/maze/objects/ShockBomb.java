/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBomb;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class ShockBomb extends AbstractBomb {
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
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanShockObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Shock the ground, too
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanShockGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
