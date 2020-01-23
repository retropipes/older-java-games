/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.GameManager;
import com.puttysoftware.rulemazer.generic.GenericAmulet;
import com.puttysoftware.rulemazer.maze.effects.MazeEffectConstants;

public class NormalAmulet extends GenericAmulet {
    // Constructors
    public NormalAmulet() {
        super();
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
        final GameManager gm = Main.getApplication().getGameManager();
        gm.deactivateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_POISONOUS);
    }
}