/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.generic;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public abstract class GenericInventoryableObject extends MazeObject {
    // Fields
    protected static final long SCORE_GRAB = 10L;

    // Constructors
    protected GenericInventoryableObject(final boolean isUsable,
            final int newUses) {
        super(false, isUsable, newUses, true);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        inv.addItem(this);
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
        WidgetWarren.getApplication().getGameManager()
                .addToScore(GenericInventoryableObject.SCORE_GRAB);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
