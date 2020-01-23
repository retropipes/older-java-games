/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractBomb;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class QuakeBomb extends AbstractBomb {
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
    public void useActionHook(int x, int y, int z) {
        // Earthquake
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .radialScanQuakeBomb(x, y, z, AbstractBomb.EFFECT_RADIUS);
    }
}
