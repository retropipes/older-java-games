/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericInfiniteLock;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class Tree extends GenericInfiniteLock {
    // Constructors
    public Tree() {
        super(new Axe());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            WidgetWarren.getApplication().showMessage("You need an axe");
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().decayTo(new CutTree());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UNLOCK);
    }

    @Override
    public String getName() {
        return "Tree";
    }

    @Override
    public String getPluralName() {
        return "Trees";
    }

    @Override
    public String getDescription() {
        return "Trees transform into Cut Trees when hit with an Axe.";
    }
}