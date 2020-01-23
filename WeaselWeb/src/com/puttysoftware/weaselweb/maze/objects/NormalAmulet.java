/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import java.awt.Color;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.GameManager;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.generic.GenericAmulet;

public class NormalAmulet extends GenericAmulet {
    // Constructors
    public NormalAmulet() {
        super(Color.pink);
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
        final GameManager gm = WeaselWeb.getApplication().getGameManager();
        gm.deactivateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_POISONOUS);
    }
}