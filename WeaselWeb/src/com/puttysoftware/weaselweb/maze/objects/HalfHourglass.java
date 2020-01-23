/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericTimeModifier;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

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
        WeaselWeb.getApplication().getGameManager().decay();
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueHalved();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        WeaselWeb.getApplication().getGameManager()
                .addToScore(HalfHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Half Hourglasses extend the time to solve the current level by half the initial value.";
    }
}
