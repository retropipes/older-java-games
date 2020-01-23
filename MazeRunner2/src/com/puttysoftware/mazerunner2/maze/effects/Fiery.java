/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.effects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.objects.FireAmulet;

public class Fiery extends MazeEffect {
    // Constructor
    public Fiery(final int newRounds) {
        super("Fiery", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MazeRunnerII.getApplication().getGameManager().getObjectInventory()
                .removeItem(new FireAmulet());
    }
}