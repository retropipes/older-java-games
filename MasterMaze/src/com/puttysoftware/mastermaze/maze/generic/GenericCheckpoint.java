/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericCheckpoint extends MazeObject {
    // Fields
    private GenericCheckKey key;
    private int keyCount;

    // Constructors
    protected GenericCheckpoint(final GenericCheckKey mgk) {
        super(true, false);
        this.key = mgk;
        this.keyCount = 0;
    }

    public int getKeyCount() {
        return this.keyCount;
    }

    public void setKeyCount(final int newKC) {
        this.keyCount = newKC;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericCheckpoint other = (GenericCheckpoint) obj;
        if (this.key != other.key
                && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        if (this.keyCount != other.keyCount) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.key != null ? this.key.hashCode() : 0);
        return 71 * hash + this.keyCount;
    }

    @Override
    public GenericCheckpoint clone() {
        final GenericCheckpoint copy = (GenericCheckpoint) super.clone();
        copy.key = (GenericCheckKey) this.key.clone();
        return copy;
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        me.editCheckpointProperties(this);
        return this;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        app.showMessage("You may NOT pass: you need " + this.keyCount + " "
                + this.key.getPluralName() + " to continue.");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        app.showMessage("You may pass.");
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return !(inv.isItemThere(this.key)
                && inv.getItemCount(this.key) >= this.keyCount);
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LOCK);
        this.type.set(TypeConstants.TYPE_CHECKPOINT);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return this.keyCount;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        this.keyCount = value;
    }
}