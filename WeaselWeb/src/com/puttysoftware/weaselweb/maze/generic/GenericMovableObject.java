/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import java.io.IOException;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.maze.objects.Empty;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericMovableObject extends MazeObject {
    // Fields
    private MazeObject savedObject;

    // Constructors
    protected GenericMovableObject(final boolean pushable,
            final boolean pullable) {
        super();
        this.getSolidProperties().setSolid(true);
        this.setPushable(pushable);
        this.setPullable(pullable);
        this.savedObject = new Empty();
        this.setType(TypeConstants.TYPE_MOVABLE);
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
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.savedObject = mo;
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = WeaselWeb.getApplication();
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
        this.savedObject = WeaselWeb.getApplication().getObjects()
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