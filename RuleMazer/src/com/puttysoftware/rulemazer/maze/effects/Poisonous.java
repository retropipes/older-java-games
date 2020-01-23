/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.maze.effects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.objects.PoisonousAmulet;

public class Poisonous extends MazeEffect {
    // Constructor
    public Poisonous(final int newRounds) {
        super("Poisonous", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        Main.getApplication().getMazeManager().getMaze().doPoisonousAmulet();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        Main.getApplication().getGameManager().getObjectInventory()
                .removeItem(new PoisonousAmulet());
        // Undo the effect
        Main.getApplication().getMazeManager().getMaze().undoPoisonAmulets();
    }
}