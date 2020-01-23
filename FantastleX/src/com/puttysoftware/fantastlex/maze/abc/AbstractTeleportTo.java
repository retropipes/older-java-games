/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractTeleportTo extends AbstractTeleport {
    // Fields
    private int destinationLevel;

    // Constructors
    public AbstractTeleportTo(final int tc) {
        super(false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.destinationLevel = 0;
        this.setTemplateColor(tc);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
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
        if (!(obj instanceof AbstractTeleportTo)) {
            return false;
        }
        final AbstractTeleportTo other = (AbstractTeleportTo) obj;
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
        FantastleX.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        FantastleX.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = FantastleX.getApplication().getEditor();
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