/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractCheckpoint extends AbstractDungeonObject {
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
    public boolean equals(Object obj) {
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
        AbstractCheckpoint copy = (AbstractCheckpoint) super.clone();
        copy.key = (AbstractCheckKey) this.key.clone();
        return copy;
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        DungeonEditorLogic me = DungeonDiver4.getApplication().getEditor();
        me.editCheckpointProperties(this);
        return this;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        app.showMessage("You may NOT pass: you need " + this.keyCount + " "
                + this.key.getPluralName() + " to continue.");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        app.showMessage("You may pass.");
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        return !(inv.isItemThere(this.key) && inv.getItemCount(this.key) >= this.keyCount);
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
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(int propID) {
        return this.keyCount;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        this.keyCount = value;
    }
}