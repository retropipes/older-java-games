/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWand;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class WallMakingWand extends GenericWand {
    public WallMakingWand() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Wall-Making Wand";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Wall(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_CREATE);
    }

    @Override
    public String getDescription() {
        return "Wall-Making Wands will create an ordinary wall in the target square when used.";
    }
}
