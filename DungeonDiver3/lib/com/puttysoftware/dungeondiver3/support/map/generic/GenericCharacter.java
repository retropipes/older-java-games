/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import java.io.IOException;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.objects.Empty;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

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
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    protected void writeMapObjectHook(XDataWriter writer) throws IOException {
        this.getSavedObject().writeMapObject(writer);
    }

    @Override
    protected MapObject readMapObjectHook(XDataReader reader, int formatVersion)
            throws IOException {
        this.setSavedObject(new MapObjectList().readMapObjectX(reader,
                formatVersion));
        return this;
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
