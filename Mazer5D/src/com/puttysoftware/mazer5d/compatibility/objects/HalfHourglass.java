/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTimeModifier;
import com.puttysoftware.mazer5d.compatibility.loaders.SoundConstants;
import com.puttysoftware.mazer5d.compatibility.loaders.SoundManager;
import com.puttysoftware.mazer5d.game.ObjectInventory;

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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Mazer5D.getApplication().getGameManager().decay();
        Mazer5D.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueHalved();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        Mazer5D.getApplication().getGameManager()
                .addToScore(HalfHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Half Hourglasses extend the time to solve the current level by half the initial value.";
    }
}
