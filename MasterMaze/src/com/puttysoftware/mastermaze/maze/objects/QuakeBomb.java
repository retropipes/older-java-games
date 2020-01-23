/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class QuakeBomb extends GenericBomb {
    // Constructors
    public QuakeBomb() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Quake Bomb";
    }

    @Override
    public String getPluralName() {
        return "Quake Bombs";
    }

    @Override
    public String getDescription() {
        return "Quake Bombs crack plain and one-way walls and may also cause crevasses to form when used; they act on an area of radius 3.";
    }

    @Override
    public void useActionHook(final int x, final int y, final int z) {
        // Earthquake
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanQuakeBomb(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}
