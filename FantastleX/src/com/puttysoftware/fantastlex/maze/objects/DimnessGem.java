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

public class DimnessGem extends AbstractGem {
    // Constructors
    public DimnessGem() {
        super(ColorConstants.COLOR_LIGHT_PURPLE);
    }

    @Override
    public String getName() {
        return "Dimness Gem";
    }

    @Override
    public String getPluralName() {
        return "Dimness Gems";
    }

    @Override
    public void postMoveActionHook() {
        FantastleX.getApplication().getMazeManager().getMaze()
                .decrementVisionRadius();
        SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Dimness Gems decrease the visible area by 1.";
    }
}
