/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.maze.effects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.objects.PoisonousAmulet;

public class Poisonous extends MazeEffect {
    // Constructor
    public Poisonous(final int newRounds) {
        super("Poisonous", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .doPoisonousAmulet();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        WidgetWarren.getApplication().getGameManager().getObjectInventory()
                .removeItem(new PoisonousAmulet());
        // Undo the effect
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .undoPoisonAmulets();
    }
}