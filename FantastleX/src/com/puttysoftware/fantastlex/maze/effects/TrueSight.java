/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.effects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.objects.TrueSightAmulet;

public class TrueSight extends MazeEffect {
    // Constructor
    public TrueSight(final int newRounds) {
        super("True Sight", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        FantastleX.getApplication().getGameManager().enableTrueSight();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        FantastleX.getApplication().getGameManager().getObjectInventory()
                .removeItem(new TrueSightAmulet());
        // Undo the effect
        FantastleX.getApplication().getGameManager().disableTrueSight();
    }
}