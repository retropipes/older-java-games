/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.game.GameLogicManager;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericAmulet;

public class NormalAmulet extends GenericAmulet {
    // Constructors
    public NormalAmulet() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Normal Amulet";
    }

    @Override
    public String getPluralName() {
        return "Normal Amulets";
    }

    @Override
    public String getDescription() {
        return "Normal Amulets have no special effect. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        // Deactivate other amulet effects
        final GameLogicManager gm = MasterMaze.getApplication()
                .getGameManager();
        gm.deactivateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_POISONOUS);
    }
}