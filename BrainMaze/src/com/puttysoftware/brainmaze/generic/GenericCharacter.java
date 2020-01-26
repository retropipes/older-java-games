/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import java.io.IOException;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.objects.Empty;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericCharacter extends MazeObject {
    // Fields
    private MazeObject savedObject;

    // Constructors
    protected GenericCharacter() {
        super(false, false);
        this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
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
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = BrainMaze.getApplication().getObjects()
                .readMazeObject(reader, formatVersion);
        return this;
    }
}
