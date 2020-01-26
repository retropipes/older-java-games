/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;

public abstract class GenericGround extends MapObject {
    // Constructors
    protected GenericGround() {
        super(false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GROUND);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
