/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericScoreIncreaser;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Sapphire extends GenericScoreIncreaser {
    // Fields
    private static final long SCORE_INCREASE = 250L;

    // Constructors
    public Sapphire() {
        super();
    }

    @Override
    public String getName() {
        return "Sapphire";
    }

    @Override
    public String getPluralName() {
        return "Sapphires";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getGameManager().addToScore(
                Sapphire.SCORE_INCREASE);
    }

    @Override
    public String getDescription() {
        return "Sapphires increase your score when picked up.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SAPPHIRE;
    }
}
