/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericAmulet;

public class GhostAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public GhostAmulet() {
        super(ColorConstants.COLOR_GRAY);
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
        MasterMaze
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_GHOSTLY,
                        GhostAmulet.EFFECT_DURATION);
    }
}