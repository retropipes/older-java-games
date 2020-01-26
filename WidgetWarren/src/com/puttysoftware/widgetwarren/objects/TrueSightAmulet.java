/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericAmulet;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectConstants;

public class TrueSightAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public TrueSightAmulet() {
        super();
    }

    @Override
    public String getName() {
        return "True Sight Amulet";
    }

    @Override
    public String getPluralName() {
        return "True Sight Amulets";
    }

    @Override
    public String getDescription() {
        return "True Sight Amulets grant the power to see what things really are for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        WidgetWarren.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_TRUE_SIGHT,
                TrueSightAmulet.EFFECT_DURATION);
    }
}