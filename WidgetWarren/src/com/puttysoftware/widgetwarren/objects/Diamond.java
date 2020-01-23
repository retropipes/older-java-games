/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericScoreIncreaser;

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
        WidgetWarren.getApplication().getGameManager()
                .addToScore(Diamond.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Diamonds increase your score when picked up.";
    }
}
