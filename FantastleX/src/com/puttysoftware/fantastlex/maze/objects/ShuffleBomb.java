/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBomb;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class ShuffleBomb extends AbstractBomb {
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
        FantastleX.getApplication().getMazeManager().getMaze()
                .radialScanShuffleObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}