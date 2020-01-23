/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericAmulet;
import com.puttysoftware.rulemazer.maze.effects.MazeEffectConstants;

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
        final int x = Main.getApplication().getGameManager().getPlayerManager()
                .getPlayerLocationX();
        final int y = Main.getApplication().getGameManager().getPlayerManager()
                .getPlayerLocationY();
        final int z = Main.getApplication().getGameManager().getPlayerManager()
                .getPlayerLocationZ();
        Main.getApplication().getMazeManager().getMaze().hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        Main.getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_FIERY,
                        FireAmulet.EFFECT_DURATION);
    }
}