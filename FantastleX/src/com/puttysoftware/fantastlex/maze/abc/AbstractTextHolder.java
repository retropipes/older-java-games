/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractTextHolder extends AbstractMazeObject {
    // Fields
    private String text;

    // Constructors
    protected AbstractTextHolder() {
        super(true, false);
        this.text = "Empty";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        CommonDialogs.showDialog(this.text);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TEXT_HOLDER);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        this.text = CommonDialogs.showTextInputDialogWithDefault(
                "Set Text for " + this.getName(), "Editor", this.text);
        return this;
    }

    @Override
    protected AbstractMazeObject readMazeObjectHook(final XDataReader reader,
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
        return AbstractMazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}