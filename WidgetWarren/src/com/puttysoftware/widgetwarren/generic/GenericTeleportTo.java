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

public abstract class GenericTeleportTo extends MazeObject {
    // Fields
    private int destLevel;

    // Constructors
    protected GenericTeleportTo() {
        super(false);
        this.destLevel = 0;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericTeleportTo other = (GenericTeleportTo) obj;
        if (this.destLevel != other.destLevel) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.destLevel;
        return hash;
    }

    @Override
    public GenericTeleportTo clone() {
        final GenericTeleportTo copy = (GenericTeleportTo) super.clone();
        copy.destLevel = this.destLevel;
        return copy;
    }

    public int getDestinationLevel() {
        return this.destLevel;
    }

    public void setDestinationLevel(final int level) {
        this.destLevel = level;
    }

    // Scriptability
    @Override
    public void gameProbeHook() {
        WidgetWarren.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        WidgetWarren.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WidgetWarren.getApplication().getEditor();
        me.editTeleportToDestination(this);
        return this;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FINISH);
        app.getGameManager().solvedLevelWarp(this.getDestinationLevel());
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
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return this.destLevel;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        this.destLevel = value;
    }
}
