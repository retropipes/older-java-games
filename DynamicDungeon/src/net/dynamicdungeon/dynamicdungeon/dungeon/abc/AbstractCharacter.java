/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Empty;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;

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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
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
    protected void writeDungeonObjectHook(final DatabaseWriter writer)
            throws IOException {
        this.savedObject.writeDungeonObject(writer);
    }

    @Override
    protected AbstractDungeonObject readDungeonObjectHook(
            final DatabaseReader reader, final int formatVersion)
            throws IOException {
        this.savedObject = DynamicDungeon.getApplication().getObjects()
                .readDungeonObject(reader, formatVersion);
        return this;
    }
}
