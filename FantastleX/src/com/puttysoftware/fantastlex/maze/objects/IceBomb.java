/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBomb;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class IceBomb extends AbstractBomb {
    // Constructors
    public IceBomb() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Ice Bomb";
    }

    @Override
    public String getPluralName() {
        return "Ice Bombs";
    }

    @Override
    public String getDescription() {
        return "Ice Bombs freeze anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Freeze objects that react to ice
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanFreezeObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Freeze ground, too
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanFreezeGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
