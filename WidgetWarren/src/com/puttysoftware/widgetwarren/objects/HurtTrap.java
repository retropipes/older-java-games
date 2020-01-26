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

public class HurtTrap extends GenericTrap {
    // Fields
    private int damage;

    // Constructors
    public HurtTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.damage = WidgetWarren.getApplication().getMazeManager().getMaze()
                .getMaximumHP() / 50;
        if (this.damage < 1) {
            this.damage = 1;
        }
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .doDamage(this.damage);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }
}