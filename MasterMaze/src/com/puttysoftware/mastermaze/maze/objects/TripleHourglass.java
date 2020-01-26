/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTimeModifier;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class TripleHourglass extends GenericTimeModifier {
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
            final ObjectInventory inv) {
        MasterMaze.getApplication().getGameManager().decay();
        MasterMaze.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueTripled();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        MasterMaze.getApplication().getGameManager()
                .addToScore(TripleHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Triple Hourglasses extend the time to solve the current level by triple the initial value.";
    }
}
