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

public class ExploreTrap extends GenericTrap {
    // Constructors
    public ExploreTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Explore Trap";
    }

    @Override
    public String getPluralName() {
        return "Explore Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .addVisionMode(MazeConstants.VISION_MODE_EXPLORE);
        WidgetWarren.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Explore Traps turn exploring mode on, then disappear.";
    }
}