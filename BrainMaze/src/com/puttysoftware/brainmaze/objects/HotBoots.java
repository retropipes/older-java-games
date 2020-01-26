/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericBoots;

public class HotBoots extends GenericBoots {
    // Constructors
    public HotBoots() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Hot Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Hot Boots";
    }

    @Override
    public String getDescription() {
        return "Hot Boots transform any ground into Hot Rock as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        final int x = BrainMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = BrainMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = BrainMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        BrainMaze.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }
}
