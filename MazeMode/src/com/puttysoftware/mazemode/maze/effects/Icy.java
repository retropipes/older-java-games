/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.maze.effects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.objects.IceAmulet;

public class Icy extends MazeEffect {
    // Constructor
    public Icy(final int newRounds) {
        super("Icy", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MazeMode.getApplication().getGameManager().getObjectInventory()
                .removeItem(new IceAmulet());
    }
}