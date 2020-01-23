/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractAmulet;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class PoisonousAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public PoisonousAmulet() {
        super(ColorConstants.COLOR_GREEN);
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
        FantastleX
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_POISONOUS,
                        PoisonousAmulet.EFFECT_DURATION);
    }
}