/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.GenericScoreIncreaser;

public class Ruby extends GenericScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 100L;

    // Constructors
    public Ruby() {
        super();
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
        WeaselWeb.getApplication().getGameManager()
                .addToScore(Ruby.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Rubys increase your score when picked up.";
    }
}
