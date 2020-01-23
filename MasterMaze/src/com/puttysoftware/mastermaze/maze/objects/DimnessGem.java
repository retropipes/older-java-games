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

public class DimnessGem extends GenericGem {
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
        MasterMaze.getApplication().getMazeManager().getMaze()
                .decrementVisionRadius();
        SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Dimness Gems decrease the visible area by 1.";
    }
}
