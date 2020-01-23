/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericAmulet;

public class TrueSightAmulet extends GenericAmulet {
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
        MasterMaze
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_TRUE_SIGHT,
                        TrueSightAmulet.EFFECT_DURATION);
    }
}