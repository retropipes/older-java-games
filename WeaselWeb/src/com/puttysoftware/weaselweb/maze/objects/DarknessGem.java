/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.GenericGem;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class DarknessGem extends GenericGem {
    // Constructors
    public DarknessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Darkness Gem";
    }

    @Override
    public String getPluralName() {
        return "Darkness Gems";
    }

    @Override
    public void postMoveActionHook() {
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .setVisionRadiusToMinimum();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DARKNESS);
    }

    @Override
    public String getDescription() {
        return "Darkness Gems decrease the visible area to its minimum.";
    }
}
