/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.GenericScoreIncreaser;

public class Diamond extends GenericScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 500L;

    // Constructors
    public Diamond() {
        super();
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
        WeaselWeb.getApplication().getGameManager()
                .addToScore(Diamond.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Diamonds increase your score when picked up.";
    }
}
