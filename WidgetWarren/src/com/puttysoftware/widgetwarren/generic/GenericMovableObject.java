/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.generic;

import java.io.IOException;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.objects.Empty;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericMovableObject extends MazeObject {
    // Constructors
    protected GenericMovableObject(final boolean pushable,
            final boolean pullable) {
        super(true, pushable, false, false, pullable, false, false, true, false,
                0);
        this.setSavedObject(new Empty());
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        this.setSavedObject(mo);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
        this.setSavedObject(mo);
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
    protected MazeObject readMazeObjectHookXML(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.setSavedObject(WidgetWarren.getApplication().getObjects()
                .readMazeObjectXML(reader, formatVersion));
        return this;
    }

    @Override
    protected void writeMazeObjectHookXML(final XDataWriter writer)
            throws IOException {
        this.getSavedObject().writeMazeObjectXML(writer);
    }

    @Override
    public int getCustomFormat() {
        if (WidgetWarren.getApplication().getMazeManager()
                .isMazeXML1Compatible()) {
            return 0;
        } else {
            return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
        }
    }
}