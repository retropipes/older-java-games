/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import java.io.IOException;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.objects.Empty;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;
import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public abstract class GenericMovableObject extends MazeObject {
    // Fields
    private MazeObject savedObject;

    // Constructors
    protected GenericMovableObject(final boolean pushable,
            final boolean pullable) {
        super(true, pushable, false, false, pullable, false, false, true, false,
                0);
        this.savedObject = new Empty();
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
        final Application app = Main.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.savedObject = mo;
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = Main.getApplication();
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
    protected MazeObject readMazeObjectHookXML(final XMLDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = Main.getApplication().getObjects()
                .readMazeObjectXML(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeMazeObjectHookXML(final XMLDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectXML(writer);
    }

    @Override
    public int getCustomFormat() {
        if (Main.getApplication().getMazeManager().isMazeXML1Compatible()) {
            return 0;
        } else {
            return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
        }
    }
}