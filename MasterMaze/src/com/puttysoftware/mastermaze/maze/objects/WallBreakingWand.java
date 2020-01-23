/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWand;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class WallBreakingWand extends GenericWand {
    // Constructors
    public WallBreakingWand() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getName() {
        return "Wall-Breaking Wand";
    }

    @Override
    public String getPluralName() {
        return "Wall-Breaking Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getDescription() {
        return "Wall-Breaking Wands will destroy one wall when used, if aimed at a wall.";
    }
}
