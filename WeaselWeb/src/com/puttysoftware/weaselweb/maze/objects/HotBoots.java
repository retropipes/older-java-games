/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.GenericBoots;

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
        final int x = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        WeaselWeb.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }
}
