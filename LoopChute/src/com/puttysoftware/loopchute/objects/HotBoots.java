/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericBoots;

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
        final int x = LoopChute.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = LoopChute.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = LoopChute.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        LoopChute.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }
}
