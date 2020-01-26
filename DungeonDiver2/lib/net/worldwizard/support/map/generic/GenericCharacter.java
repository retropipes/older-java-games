/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import java.io.IOException;

import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public abstract class GenericCharacter extends MapObject {
    // Constructors
    protected GenericCharacter() {
        super(false);
        this.setSavedObject(new Empty());
    }

    // Methods
    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public int getCustomFormat() {
        return MapObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void writeMapObjectHookX(final XDataWriter writer)
            throws IOException {
        this.getSavedObject().writeMapObjectX(writer);
    }

    @Override
    protected MapObject readMapObjectHookX(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.setSavedObject(
                new MapObjectList().readMapObjectX(reader, formatVersion));
        return this;
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
