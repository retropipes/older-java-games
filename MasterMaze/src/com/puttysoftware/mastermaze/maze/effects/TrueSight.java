/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.effects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.objects.TrueSightAmulet;

public class TrueSight extends MazeEffect {
    // Constructor
    public TrueSight(final int newRounds) {
        super("True Sight", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        MasterMaze.getApplication().getGameManager().enableTrueSight();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        MasterMaze.getApplication().getGameManager().getObjectInventory()
                .removeItem(new TrueSightAmulet());
        // Undo the effect
        MasterMaze.getApplication().getGameManager().disableTrueSight();
    }
}