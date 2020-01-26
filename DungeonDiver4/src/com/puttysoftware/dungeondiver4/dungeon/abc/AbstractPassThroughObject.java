/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractPassThroughObject extends AbstractDungeonObject {
    // Constructors
    protected AbstractPassThroughObject() {
        super(false, false);
    }

    protected AbstractPassThroughObject(final boolean sightBlock) {
        super(false, sightBlock);
    }

    protected AbstractPassThroughObject(final boolean acceptPushInto,
            final boolean acceptPushOut, final boolean acceptPullInto,
            final boolean acceptPullOut) {
        super(false, false, acceptPushInto, acceptPushOut, false,
                acceptPullInto, acceptPullOut, true, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
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