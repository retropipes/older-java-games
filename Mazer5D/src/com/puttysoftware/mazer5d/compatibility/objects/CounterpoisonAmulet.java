/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericAmulet;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class CounterpoisonAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public CounterpoisonAmulet() {
        super();
    }

    @Override
    public String getName() {
        return "Counterpoison Amulet";
    }

    @Override
    public String getPluralName() {
        return "Counterpoison Amulets";
    }

    @Override
    public String getDescription() {
        return "Counterpoison Amulets grant the power to make the air less poisonous for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_COUNTER_POISONED,
                CounterpoisonAmulet.EFFECT_DURATION);
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.COUNTERPOISON_AMULET;
    }
}