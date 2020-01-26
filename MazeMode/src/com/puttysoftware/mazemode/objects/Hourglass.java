/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericTimeModifier;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class Hourglass extends GenericTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 10L;

    // Constructors
    public Hourglass() {
        super();
    }

    @Override
    public String getName() {
        return "Hourglass";
    }

    @Override
    public String getPluralName() {
        return "Hourglasses";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        MazeMode.getApplication().getGameManager().decay();
        MazeMode.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValue();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        MazeMode.getApplication().getGameManager()
                .addToScore(Hourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Hourglasses extend the time to solve the current level by the initial value.";
    }
}
