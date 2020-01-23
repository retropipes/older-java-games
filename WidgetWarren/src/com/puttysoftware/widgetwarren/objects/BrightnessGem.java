/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericGem;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class BrightnessGem extends GenericGem {
    // Constructors
    public BrightnessGem() {
        super();
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
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .setVisionRadiusToMaximum();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_LIGHT);
    }

    @Override
    public String getDescription() {
        return "Brightness Gems increase the visible area to its maximum.";
    }
}
