/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.maze.effects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Ghostly extends MazeEffect {
    // Constructor
    public Ghostly(final int newRounds) {
        super("Ghostly", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        Mazer5D.getBagOStuff().getGameManager().getObjectInventory().removeItem(
                MazeObjects.GHOST_AMULET);
    }
}