/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBomb;

public class IceBomb extends GenericBomb {
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
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanFreezeObjects(x, y, z, GenericBomb.EFFECT_RADIUS);
        // Freeze ground, too
        MasterMaze.getApplication().getMazeManager().getMaze()
                .radialScanFreezeGround(x, y, z, GenericBomb.EFFECT_RADIUS);
    }
}
