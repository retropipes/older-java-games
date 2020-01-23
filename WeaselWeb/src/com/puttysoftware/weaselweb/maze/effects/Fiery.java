/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.effects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.objects.FireAmulet;

public class Fiery extends MazeEffect {
    // Constructor
    public Fiery(final int newRounds) {
        super("Fiery", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        WeaselWeb.getApplication().getGameManager().getObjectInventory()
                .removeItem(new FireAmulet());
    }
}