/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.game.GameLogicManager;
import com.puttysoftware.fantastlex.maze.abc.AbstractAmulet;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class NormalAmulet extends AbstractAmulet {
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
        final GameLogicManager gm = FantastleX.getApplication()
                .getGameManager();
        gm.deactivateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_POISONOUS);
    }
}