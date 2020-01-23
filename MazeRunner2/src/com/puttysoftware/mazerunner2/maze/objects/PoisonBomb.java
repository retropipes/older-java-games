/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractBomb;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

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
    public void useActionHook(int x, int y, int z) {
        // Poison objects that react to poison
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .radialScanPoisonObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Poison the ground, too
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .radialScanPoisonGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
