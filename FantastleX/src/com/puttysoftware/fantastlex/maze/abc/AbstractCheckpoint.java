/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractCheckpoint extends AbstractMazeObject {
    // Fields
    private AbstractCheckKey key;
    private int keyCount;

    // Constructors
    protected AbstractCheckpoint(final AbstractCheckKey mgk) {
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
        final AbstractCheckpoint other = (AbstractCheckpoint) obj;
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
    public AbstractCheckpoint clone() {
        final AbstractCheckpoint copy = (AbstractCheckpoint) super.clone();
        copy.key = (AbstractCheckKey) this.key.clone();
        return copy;
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = FantastleX.getApplication().getEditor();
        me.editCheckpointProperties(this);
        return this;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.showMessage("You may NOT pass: you need " + this.keyCount + " "
                + this.key.getPluralName() + " to continue.");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.showMessage("You may pass.");
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
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