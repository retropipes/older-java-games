/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractAntiObject extends AbstractDungeonObject {
    // Constructors
    protected AbstractAntiObject() {
        super(false, false, true, true, false, true, true, false, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_ANTI_OBJECT);
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
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
