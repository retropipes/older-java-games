/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public abstract class AbstractGround extends AbstractDungeonObject {
    // Constructors
    protected AbstractGround(final int tc) {
        super(false, false);
        this.setTemplateColor(tc);
    }

    protected AbstractGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final int tc) {
        super(false, false, doesAcceptPushInto, doesAcceptPushOut, false,
                doesAcceptPullInto, doesAcceptPullOut, true, false, false,
                false);
        this.setTemplateColor(tc);
    }

    protected AbstractGround(final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction,
            final int tc) {
        super(false, false, doesAcceptPushInto, doesAcceptPushOut, false,
                doesAcceptPullInto, doesAcceptPullOut, hasFriction, false,
                false, false);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TEXTURED;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GROUND);
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Do nothing
    }
}
