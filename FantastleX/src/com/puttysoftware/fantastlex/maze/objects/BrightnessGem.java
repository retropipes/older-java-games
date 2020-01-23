/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractGem;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class BrightnessGem extends AbstractGem {
    // Constructors
    public BrightnessGem() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getName() {
        return "Brightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Brightness Gems";
    }

    @Override
    public void postMoveActionHook() {
        FantastleX.getApplication().getMazeManager().getMaze()
                .setVisionRadiusToMaximum();
        SoundManager.playSound(SoundConstants.SOUND_LIGHT);
    }

    @Override
    public String getDescription() {
        return "Brightness Gems increase the visible area to its maximum.";
    }
}
