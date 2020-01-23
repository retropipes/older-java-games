/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericTimeModifier;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class HalfHourglass extends GenericTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 5L;

    // Constructors
    public HalfHourglass() {
        super();
    }

    @Override
    public String getName() {
        return "Half Hourglass";
    }

    @Override
    public String getPluralName() {
        return "Half Hourglasses";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        Main.getApplication().getGameManager().decay();
        Main.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueHalved();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        Main.getApplication().getGameManager()
                .addToScore(HalfHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Half Hourglasses extend the time to solve the current level by half the initial value.";
    }
}
