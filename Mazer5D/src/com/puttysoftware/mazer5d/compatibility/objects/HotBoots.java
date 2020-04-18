/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericBoots;

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
        final int x = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Mazer5D.getBagOStuff().getMazeManager().getMaze().hotGround(x, y, z);
    }
}
