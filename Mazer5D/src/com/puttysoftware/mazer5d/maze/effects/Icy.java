/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.maze.effects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.objects.IceAmulet;

public class Icy extends MazeEffect {
    // Constructor
    public Icy(final int newRounds) {
        super("Icy", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        Mazer5D.getApplication().getGameManager().getObjectInventory()
                .removeItem(new IceAmulet());
    }
}