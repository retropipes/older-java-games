/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.maze.effects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.objects.TrueSightAmulet;

public class TrueSight extends MazeEffect {
    // Constructor
    public TrueSight(final int newRounds) {
        super("True Sight", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        Main.getApplication().getGameManager().enableTrueSight();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        Main.getApplication().getGameManager().getObjectInventory()
                .removeItem(new TrueSightAmulet());
        // Undo the effect
        Main.getApplication().getGameManager().disableTrueSight();
    }
}