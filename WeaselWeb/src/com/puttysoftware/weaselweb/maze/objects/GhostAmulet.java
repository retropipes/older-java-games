/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import java.awt.Color;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericAmulet;

public class GhostAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public GhostAmulet() {
        super(Color.white);
    }

    @Override
    public String getName() {
        return "Ghost Amulet";
    }

    @Override
    public String getPluralName() {
        return "Ghost Amulets";
    }

    @Override
    public String getDescription() {
        return "Ghost Amulets grant the power to walk through walls for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        WeaselWeb.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_GHOSTLY,
                GhostAmulet.EFFECT_DURATION);
    }
}