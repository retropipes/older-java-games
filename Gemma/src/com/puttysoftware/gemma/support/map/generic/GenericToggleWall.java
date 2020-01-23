/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import com.puttysoftware.gemma.support.map.MapConstants;

public abstract class GenericToggleWall extends MapObject {
    // Fields
    private final boolean state;

    // Constructors
    protected GenericToggleWall(boolean solidState, TemplateTransform tt) {
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
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
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
