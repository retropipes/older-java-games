/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.abstractobjects;

import java.io.IOException;

import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.objects.Empty;
import com.puttysoftware.ltremix.utilities.ArenaConstants;
import com.puttysoftware.ltremix.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractCharacter extends AbstractArenaObject {
    // Constructors
    protected AbstractCharacter(final boolean useTimer, final int instance) {
        super(true);
        this.setSavedObject(new Empty());
        if (useTimer) {
            this.activateTimer(1);
        }
        this.type.set(TypeConstants.TYPE_CHARACTER);
        this.setInstanceNum(instance);
    }

    // Methods
    @Override
    public void postMoveAction(final int dirX, final int dirY, final int dirZ) {
        // Do nothing
    }

    @Override
    public int getPrimaryLayer() {
        return ArenaConstants.LAYER_UPPER_OBJECTS;
    }

    @Override
    public int getCustomFormat() {
        return AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractArenaObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public void timerExpiredAction(final int x, final int y) {
        if (this.getSavedObject() instanceof AbstractMovableObject) {
            this.getSavedObject().timerExpiredAction(x, y);
        }
        this.activateTimer(1);
    }

    @Override
    protected void writeArenaObjectHook(final XDataWriter writer)
            throws IOException {
        this.getSavedObject().writeArenaObject(writer);
        writer.writeInt(this.getInstanceNum());
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG2(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG2(reader, formatVersion));
        return this;
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG3(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG3(reader, formatVersion));
        return this;
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG4(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG4(reader, formatVersion));
        return this;
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG5(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG5(reader, formatVersion));
        return this;
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG6(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG6(reader, formatVersion));
        this.setInstanceNum(reader.readInt());
        return this;
    }

    @Override
    protected AbstractArenaObject readArenaObjectHookG7(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.setSavedObject(LTRemix.getApplication().getObjects()
                .readArenaObjectG7(reader, formatVersion));
        this.setInstanceNum(reader.readInt());
        return this;
    }
}