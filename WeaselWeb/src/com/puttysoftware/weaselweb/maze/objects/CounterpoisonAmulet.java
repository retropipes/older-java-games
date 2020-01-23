/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import java.awt.Color;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericAmulet;

public class CounterpoisonAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public CounterpoisonAmulet() {
        super(Color.yellow);
    }

    @Override
    public String getName() {
        return "Counterpoison Amulet";
    }

    @Override
    public String getPluralName() {
        return "Counterpoison Amulets";
    }

    @Override
    public String getDescription() {
        return "Counterpoison Amulets grant the power to make the air less poisonous for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        WeaselWeb
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED,
                        CounterpoisonAmulet.EFFECT_DURATION);
    }
}