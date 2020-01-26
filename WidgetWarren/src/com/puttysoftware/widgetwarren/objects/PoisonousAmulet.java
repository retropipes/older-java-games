/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericAmulet;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectConstants;

public class PoisonousAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public PoisonousAmulet() {
        super();
    }

    @Override
    public String getName() {
        return "Poisonous Amulet";
    }

    @Override
    public String getPluralName() {
        return "Poisonous Amulets";
    }

    @Override
    public String getDescription() {
        return "Poisonous Amulets grant the power to make the air more poisonous for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        WidgetWarren.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_POISONOUS,
                PoisonousAmulet.EFFECT_DURATION);
    }
}