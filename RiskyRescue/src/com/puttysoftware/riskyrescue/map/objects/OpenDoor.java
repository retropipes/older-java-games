/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.map.MapConstants;

public class OpenDoor extends MapObject {
    // Constructors
    public OpenDoor() {
        super(ObjectImage.OPEN_DOOR, false, false);
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
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
    public String getName() {
        return "Open Door";
    }

    @Override
    public String getPluralName() {
        return "Open Doors";
    }

    @Override
    public String getDescription() {
        return "Open Doors are purely decorative.";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
