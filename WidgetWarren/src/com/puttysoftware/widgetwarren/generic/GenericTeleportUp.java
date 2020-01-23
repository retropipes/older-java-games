/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.generic;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.MazeEditor;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public abstract class GenericTeleportUp extends MazeObject {
    // Constructors
    protected GenericTeleportUp() {
        super(false);
    }

    protected GenericTeleportUp(final boolean doesAcceptPushInto) {
        super(false, false, doesAcceptPushInto, false, false, false, false,
                true, false, 0);
    }

    // Methods
    public int getDestinationRow() {
        final Application app = WidgetWarren.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationX();
    }

    public int getDestinationColumn() {
        final Application app = WidgetWarren.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationY();
    }

    public int getDestinationFloor() {
        final Application app = WidgetWarren.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationZ() + 1;
    }

    public int getDestinationLevel() {
        final Application app = WidgetWarren.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UP);
    }

    @Override
    public void editorPlaceHook() {
        final MazeEditor me = WidgetWarren.getApplication().getEditor();
        me.pairStairs(MazeEditor.STAIRS_UP);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
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
