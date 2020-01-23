/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericAmulet;
import com.puttysoftware.mazemode.maze.effects.MazeEffectConstants;

public class IceAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public IceAmulet() {
        super();
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
        MazeMode.getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_ICY,
                        IceAmulet.EFFECT_DURATION);
    }
}