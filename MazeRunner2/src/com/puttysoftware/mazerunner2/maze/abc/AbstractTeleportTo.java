/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractTeleportTo extends AbstractTeleport {
    // Fields
    private int destinationLevel;

    // Constructors
    public AbstractTeleportTo(int tc) {
        super(false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.destinationLevel = 0;
        this.setTemplateColor(tc);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AbstractTeleportTo)) {
            return false;
        }
        AbstractTeleportTo other = (AbstractTeleportTo) obj;
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

    public void setDestinationLevel(int level) {
        this.destinationLevel = level;
    }

    @Override
    public void gameProbeHook() {
        MazeRunnerII.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        MazeRunnerII.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        me.editFinishToDestination(this);
        return this;
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(int propID) {
        return this.destinationLevel;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        this.destinationLevel = value;
    }
}