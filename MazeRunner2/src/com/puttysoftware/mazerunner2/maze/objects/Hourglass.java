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
    public void postMoveAction(boolean ie, int dirX, int dirY,
            MazeObjectInventory inv) {
        MazeRunnerII.getApplication().getGameManager().decay();
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValue();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(Hourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Hourglasses extend the time to solve the current level by the initial value.";
    }
}
