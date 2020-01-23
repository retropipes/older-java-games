/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTrap;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class HealTrap extends GenericTrap {
    // Fields
    private int healing;

    // Constructors
    public HealTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        this.healing = WidgetWarren.getApplication().getMazeManager().getMaze()
                .getMaximumHP() / 50;
        if (this.healing < 1) {
            this.healing = 1;
        }
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .heal(this.healing);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Heal Traps heal you when stepped on.";
    }
}