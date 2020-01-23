/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericAmulet;
import com.puttysoftware.rulemazer.maze.effects.MazeEffectConstants;

public class GhostAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public GhostAmulet() {
        super();
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
        Main.getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_GHOSTLY,
                        GhostAmulet.EFFECT_DURATION);
    }
}