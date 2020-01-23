/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericScoreIncreaser;

public class Amethyst extends GenericScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 50L;

    // Constructors
    public Amethyst() {
        super();
    }

    @Override
    public String getName() {
        return "Amethyst";
    }

    @Override
    public String getPluralName() {
        return "Amethysts";
    }

    @Override
    public void postMoveActionHook() {
        MazeMode.getApplication().getGameManager()
                .addToScore(Amethyst.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Amethysts increase your score when picked up.";
    }
}
