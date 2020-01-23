/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBoots;

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
        final int x = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MasterMaze.getApplication().getMazeManager().getMaze()
                .hotGround(x, y, z);
    }
}
