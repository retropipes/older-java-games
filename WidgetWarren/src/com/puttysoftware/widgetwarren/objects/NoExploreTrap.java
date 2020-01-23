/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTrap;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class NoExploreTrap extends GenericTrap {
    // Constructors
    public NoExploreTrap() {
        super();
    }

    @Override
    public String getName() {
        return "No Explore Trap";
    }

    @Override
    public String getPluralName() {
        return "No Explore Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .removeVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        WidgetWarren.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "No Explore Traps turn exploring mode off, then disappear.";
    }
}