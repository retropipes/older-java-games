/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractAmulet;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class TrueSightAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public TrueSightAmulet() {
        super(ColorConstants.COLOR_BLUE);
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
        FantastleX
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_TRUE_SIGHT,
                        TrueSightAmulet.EFFECT_DURATION);
    }
}