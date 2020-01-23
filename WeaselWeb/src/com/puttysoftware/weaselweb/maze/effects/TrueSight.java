/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.effects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.objects.TrueSightAmulet;

public class TrueSight extends MazeEffect {
    // Constructor
    public TrueSight(final int newRounds) {
        super("True Sight", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        WeaselWeb.getApplication().getGameManager().enableTrueSight();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        WeaselWeb.getApplication().getGameManager().getObjectInventory()
                .removeItem(new TrueSightAmulet());
        // Undo the effect
        WeaselWeb.getApplication().getGameManager().disableTrueSight();
    }
}