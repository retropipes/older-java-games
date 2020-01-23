/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import java.io.IOException;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public abstract class AbstractCharacter extends AbstractMazeObject {
    // Fields
    private AbstractMazeObject savedObject;

    // Constructors
    protected AbstractCharacter() {
        super(false, false);
        this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
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
        return AbstractMazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    protected void writeMazeObjectHook(XDataWriter writer) throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected AbstractMazeObject readLegacyMazeObjectHook(
            XLegacyDataReader reader, int formatVersion) throws IOException {
        this.savedObject = MazeRunnerII.getApplication().getObjects()
                .readLegacyMazeObject(reader, formatVersion);
        return this;
    }

    @Override
    protected AbstractMazeObject readMazeObjectHook(XDataReader reader,
            int formatVersion) throws IOException {
        this.savedObject = MazeRunnerII.getApplication().getObjects()
                .readMazeObject(reader, formatVersion);
        return this;
    }
}
