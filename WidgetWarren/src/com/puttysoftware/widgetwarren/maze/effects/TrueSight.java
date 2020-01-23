/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.maze.effects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.objects.TrueSightAmulet;

public class TrueSight extends MazeEffect {
    // Constructor
    public TrueSight(final int newRounds) {
        super("True Sight", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        WidgetWarren.getApplication().getGameManager().enableTrueSight();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        WidgetWarren.getApplication().getGameManager().getObjectInventory()
                .removeItem(new TrueSightAmulet());
        // Undo the effect
        WidgetWarren.getApplication().getGameManager().disableTrueSight();
    }
}