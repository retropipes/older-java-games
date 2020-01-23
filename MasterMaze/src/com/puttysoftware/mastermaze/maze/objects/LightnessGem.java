/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericGem;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class LightnessGem extends GenericGem {
    // Constructors
    public LightnessGem() {
        super(ColorConstants.COLOR_LIGHT_YELLOW);
    }

    @Override
    public String getName() {
        return "Lightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Lightness Gems";
    }

    @Override
    public void postMoveActionHook() {
        MasterMaze.getApplication().getMazeManager().getMaze()
                .incrementVisionRadius();
        SoundManager.playSound(SoundConstants.SOUND_LIGHT);
    }

    @Override
    public String getDescription() {
        return "Lightness Gems increase the visible area by 1.";
    }
}
