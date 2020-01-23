/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractScoreIncreaser;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class Ruby extends AbstractScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 100L;

    // Constructors
    public Ruby() {
        super(ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "Ruby";
    }

    @Override
    public String getPluralName() {
        return "Rubys";
    }

    @Override
    public void postMoveActionHook() {
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(Ruby.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Rubys increase your score when picked up.";
    }
}
