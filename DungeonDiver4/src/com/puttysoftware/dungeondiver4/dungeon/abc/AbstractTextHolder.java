/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractTextHolder extends AbstractDungeonObject {
    // Fields
    private String text;

    // Constructors
    protected AbstractTextHolder() {
        super(true, false);
        this.text = "Empty";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        CommonDialogs.showDialog(this.text);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TEXT_HOLDER);
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
    public AbstractDungeonObject editorPropertiesHook() {
        this.text = CommonDialogs.showTextInputDialogWithDefault(
                "Set Text for " + this.getName(), "Editor", this.text);
        return this;
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Blacklist object
        return false;
    }

    @Override
    protected AbstractDungeonObject readDungeonObjectHook(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.text = reader.readString();
        return this;
    }

    @Override
    protected void writeDungeonObjectHook(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.text);
    }

    @Override
    public int getCustomFormat() {
        return AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}