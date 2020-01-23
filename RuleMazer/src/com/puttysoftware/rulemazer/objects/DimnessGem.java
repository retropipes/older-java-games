/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericGem;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class DimnessGem extends GenericGem {
    // Constructors
    public DimnessGem() {
        super();
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
        Main.getApplication().getMazeManager().getMaze()
                .decrementVisionRadius();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Dimness Gems decrease the visible area by 1.";
    }
}
