/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericTeleportTo extends GenericTeleport {
    // Fields
    private int destinationLevel;

    // Constructors
    public GenericTeleportTo(final int tc) {
        super(false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.destinationLevel = 0;
        this.setTemplateColor(tc);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_FINISH);
        app.getGameManager().solvedLevelWarp(this.getDestinationLevel());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.destinationLevel;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof GenericTeleportTo)) {
            return false;
        }
        final GenericTeleportTo other = (GenericTeleportTo) obj;
        if (this.destinationLevel != other.destinationLevel) {
            return false;
        }
        return true;
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HOUSE;
    }

    @Override
    public int getDestinationLevel() {
        return this.destinationLevel;
    }

    public void setDestinationLevel(final int level) {
        this.destinationLevel = level;
    }

    @Override
    public void gameProbeHook() {
        MasterMaze.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        MasterMaze.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        me.editFinishToDestination(this);
        return this;
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return this.destinationLevel;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        this.destinationLevel = value;
    }
}