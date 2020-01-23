/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericScoreIncreaser;

public class Diamond extends GenericScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 500L;

    // Constructors
    public Diamond() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Diamond";
    }

    @Override
    public String getPluralName() {
        return "Diamonds";
    }

    @Override
    public void postMoveActionHook() {
        MasterMaze.getApplication().getGameManager()
                .addToScore(Diamond.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Diamonds increase your score when picked up.";
    }
}
