/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.effects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.objects.CounterpoisonAmulet;

public class CounterPoisoned extends MazeEffect {
    // Constructor
    public CounterPoisoned(final int newRounds) {
        super("Counter-Poisoned", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .doCounterpoisonAmulet();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MazeRunnerII.getApplication().getGameManager().getObjectInventory()
                .removeItem(new CounterpoisonAmulet());
        // Undo the effect
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .undoPoisonAmulets();
    }
}