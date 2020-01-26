/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericInventoryableObject;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class SunStone extends GenericInventoryableObject {
    // Constants
    private static final long SCORE_GRAB_STONE = 1L;

    // Constructors
    public SunStone() {
        super(false, 0);
    }

    @Override
    public String getName() {
        return "Sun Stone";
    }

    @Override
    public String getPluralName() {
        return "Sun Stones";
    }

    @Override
    public String getDescription() {
        return "Sun Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        inv.addItem(this);
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SUN_STONE);
        WidgetWarren.getApplication().getGameManager()
                .addToScore(SunStone.SCORE_GRAB_STONE);
    }
}