/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericAmulet;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectConstants;

public class FireAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super();
    }

    @Override
    public String getName() {
        return "Fire Amulet";
    }

    @Override
    public String getPluralName() {
        return "Fire Amulets";
    }

    @Override
    public String getDescription() {
        return "Fire Amulets grant the power to transform ground into Hot Rock for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void stepAction() {
        final int x = WidgetWarren.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = WidgetWarren.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = WidgetWarren.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        WidgetWarren.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }

    @Override
    public void postMoveActionHook() {
        WidgetWarren.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}