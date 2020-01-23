/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.maze.effects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.objects.CounterpoisonAmulet;

public class CounterPoisoned extends MazeEffect {
    // Constructor
    public CounterPoisoned(final int newRounds) {
        super("Counter-Poisoned", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        MazeMode.getApplication().getMazeManager().getMaze()
                .doCounterpoisonAmulet();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MazeMode.getApplication().getGameManager().getObjectInventory()
                .removeItem(new CounterpoisonAmulet());
        // Undo the effect
        MazeMode.getApplication().getMazeManager().getMaze()
                .undoPoisonAmulets();
    }
}