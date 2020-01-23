/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.maze.effects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.objects.FireAmulet;

public class Fiery extends MazeEffect {
    // Constructor
    public Fiery(final int newRounds) {
        super("Fiery", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        WidgetWarren.getApplication().getGameManager().getObjectInventory()
                .removeItem(new FireAmulet());
    }
}