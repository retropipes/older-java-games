/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import java.awt.Color;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericAmulet;

public class FireAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super(Color.red);
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
        final int x = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        WeaselWeb.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }

    @Override
    public void postMoveActionHook() {
        WeaselWeb.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}