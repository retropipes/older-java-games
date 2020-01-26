/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractMovableObject extends AbstractDungeonObject {
    // Constructors
    protected AbstractMovableObject(final boolean pushable,
            final boolean pullable, final int attrID) {
        super(true, pushable, false, false, pullable, false, false, true,
                false);
        this.setSavedObject(new Empty());
        this.setAttributeID(attrID);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void pushAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
    }

    @Override
    public void pullAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pullX, final int pullY) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MOVABLE);
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