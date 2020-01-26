/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractTimeModifier;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class TripleHourglass extends AbstractTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 5L;

    // Constructors
    public TripleHourglass() {
        super(ObjectImageConstants.OBJECT_IMAGE_SMALL_3,
                ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Triple Hourglass";
    }

    @Override
    public String getPluralName() {
        return "Triple Hourglasses";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        FantastleX.getApplication().getGameManager().decay();
        FantastleX.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueTripled();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        FantastleX.getApplication().getGameManager()
                .addToScore(TripleHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Triple Hourglasses extend the time to solve the current level by triple the initial value.";
    }
}
