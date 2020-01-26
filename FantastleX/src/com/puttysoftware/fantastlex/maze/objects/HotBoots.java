/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class HotBoots extends AbstractBoots {
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
        final int x = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        FantastleX.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }
}
