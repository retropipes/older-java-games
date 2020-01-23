/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericAmulet;
import com.puttysoftware.mazemode.maze.effects.MazeEffectConstants;

public class FireAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super();
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
        final int x = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        MazeMode.getApplication().getMazeManager().getMaze().hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        MazeMode.getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_FIERY,
                        FireAmulet.EFFECT_DURATION);
    }
}