/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.io.IOException;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractCharacter extends AbstractDungeonObject {
    // Fields
    private AbstractDungeonObject savedObject;

    // Constructors
    protected AbstractCharacter() {
        super(false, false);
        this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        // Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public int getCustomFormat() {
        return AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void writeDungeonObjectHook(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeDungeonObject(writer);
    }

    @Override
    protected AbstractDungeonObject readDungeonObjectHook(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.savedObject = DungeonDiver4.getApplication().getObjects()
                .readDungeonObject(reader, formatVersion);
        return this;
    }
}
