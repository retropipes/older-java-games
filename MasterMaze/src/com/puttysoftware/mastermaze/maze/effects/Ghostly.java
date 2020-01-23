/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.effects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.objects.GhostAmulet;

public class Ghostly extends MazeEffect {
    // Constructor
    public Ghostly(final int newRounds) {
        super("Ghostly", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MasterMaze.getApplication().getGameManager().getObjectInventory()
                .removeItem(new GhostAmulet());
    }
}