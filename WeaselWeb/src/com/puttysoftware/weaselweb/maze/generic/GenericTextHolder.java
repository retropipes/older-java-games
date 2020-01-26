/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericTextHolder extends MazeObject {
    // Fields
    private String text;

    // Constructors
    protected GenericTextHolder() {
        super();
        this.getSolidProperties().setSolid(true);
        this.text = "Empty";
        this.setType(TypeConstants.TYPE_TEXT_HOLDER);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        CommonDialogs.showDialog(this.text);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public MazeObject editorPropertiesHook() {
        this.text = CommonDialogs.showTextInputDialogWithDefault(
                "Set Text for " + this.getName(), "Editor", this.text);
        return this;
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.text = reader.readString();
        return this;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.text);
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}