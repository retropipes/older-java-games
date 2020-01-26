/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractLock extends AbstractDungeonObject {
    // Fields
    private AbstractKey key;
    protected static final long SCORE_UNLOCK = 5L;

    // Constructors
    protected AbstractLock(final AbstractKey mgk) {
        super(true, false);
        this.key = mgk;
    }

    protected AbstractLock(final AbstractKey mgk,
            final boolean doesAcceptPushInto) {
        super(true, false, doesAcceptPushInto, false, false, false, false, true,
                false);
        this.key = mgk;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractLock other = (AbstractLock) obj;
        if (this.key != other.key
                && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int hash = 7;
        return 71 * hash + (this.key != null ? this.key.hashCode() : 0);
    }

    @Override
    public AbstractLock clone() {
        final AbstractLock copy = (AbstractLock) super.clone();
        copy.key = this.key.clone();
        return copy;
    }

    // Accessor methods
    public AbstractKey getKey() {
        return this.key;
    }

    public void setKey(final AbstractKey newKey) {
        this.key = newKey;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        if (!inv.isItemThere(new PasswallBoots())) {
            if (!this.key.isInfinite()) {
                inv.removeItem(this.key);
            }
            app.getGameManager().decay();
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        // Handle passwall boots
        if (inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return !inv.isItemThere(this.key);
        }
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}