/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTimeModifier;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

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
        MazeRunnerII.getApplication().getGameManager().decay();
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueTripled();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(TripleHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Triple Hourglasses extend the time to solve the current level by triple the initial value.";
    }
}
