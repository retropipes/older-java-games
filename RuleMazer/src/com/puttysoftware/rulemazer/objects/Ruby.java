/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericScoreIncreaser;

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
        Main.getApplication().getGameManager().addToScore(Ruby.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Rubys increase your score when picked up.";
    }
}
