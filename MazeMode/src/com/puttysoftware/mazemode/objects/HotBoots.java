/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericBoots;

public class HotBoots extends GenericBoots {
    // Constructors
    public HotBoots() {
        super();
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
        final int x = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = MazeMode.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        MazeMode.getApplication().getMazeManager().getMaze().hotGround(x, y, z);
    }
}
