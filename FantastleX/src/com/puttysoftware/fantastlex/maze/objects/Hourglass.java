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

public class Hourglass extends AbstractTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 10L;

    // Constructors
    public Hourglass() {
        super(ObjectImageConstants.OBJECT_IMAGE_SMALL_1,
                ColorConstants.COLOR_GREEN);
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
            final MazeObjectInventory inv) {
        FantastleX.getApplication().getGameManager().decay();
        FantastleX.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValue();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        FantastleX.getApplication().getGameManager()
                .addToScore(Hourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Hourglasses extend the time to solve the current level by the initial value.";
    }
}
