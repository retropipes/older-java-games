/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractWand;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class WallMakingWand extends AbstractWand {
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
