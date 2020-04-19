/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericAmulet;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazer5d.game.GameManager;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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
        final GameManager gm = Mazer5D.getBagOStuff().getGameManager();
        gm.deactivateEffect(MazeEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(MazeEffectConstants.EFFECT_POISONOUS);
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.NORMAL_AMULET;
    }}