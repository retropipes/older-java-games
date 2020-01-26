/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import java.io.IOException;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.objects.Empty;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericMovableObject extends MazeObject {
    // Fields
    private MazeObject savedObject;

    // Constructors
    protected GenericMovableObject(final boolean pushable,
            final boolean pullable, final String attrName) {
        super(true, pushable, false, false, pullable, false, false, true,
                false);
        this.savedObject = new Empty();
        this.setAttributeName(attrName);
    }

    @Override
    public final String getBaseName() {
        return "block_base";
    }

    @Override
    public boolean canMove() {
        return true;
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObject obj) {
        this.savedObject = obj;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = BrainMaze.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.savedObject = mo;
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = BrainMaze.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
        this.savedObject = mo;
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PULL);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MOVABLE);
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
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = BrainMaze.getApplication().getObjects()
                .readMazeObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}