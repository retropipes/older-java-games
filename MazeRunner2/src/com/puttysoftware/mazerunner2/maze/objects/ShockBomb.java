/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractBomb;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

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
    public void useActionHook(int x, int y, int z) {
        // Shock objects that react to shock
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .radialScanShockObjects(x, y, z, AbstractBomb.EFFECT_RADIUS);
        // Shock the ground, too
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .radialScanShockGround(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
