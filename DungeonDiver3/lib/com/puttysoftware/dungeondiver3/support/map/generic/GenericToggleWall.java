/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;

public abstract class GenericToggleWall extends MapObject {
    // Fields
    private final boolean state;

    // Constructors
    protected GenericToggleWall(final boolean solidState,
            final TemplateTransform tt) {
        super(solidState);
        this.state = solidState;
        this.setTemplateTransform(tt);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TOGGLE_WALL);
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
    public String getGameImageNameHook() {
        if (this.state) {
            return "wall";
        } else {
            return "off wall";
        }
    }

    @Override
    public String getEditorImageNameHook() {
        if (this.state) {
            return "wall";
        } else {
            return "off wall";
        }
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
