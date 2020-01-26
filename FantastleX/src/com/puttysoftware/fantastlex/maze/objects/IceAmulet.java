/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractAmulet;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class IceAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public IceAmulet() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Ice Amulet";
    }

    @Override
    public String getPluralName() {
        return "Ice Amulets";
    }

    @Override
    public String getDescription() {
        return "Ice Amulets grant the power to make ground frictionless for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        FantastleX.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_ICY, IceAmulet.EFFECT_DURATION);
    }
}