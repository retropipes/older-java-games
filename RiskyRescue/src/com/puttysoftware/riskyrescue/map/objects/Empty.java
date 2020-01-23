/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.map.MapConstants;

public class Empty extends MapObject {
    // Constructors
    public Empty() {
        super(ObjectImage.EMPTY, false, false);
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
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Squares of Emptiness";
    }

    @Override
    public String getDescription() {
        return "Squares of Emptiness are what fills areas that aren't occupied by other objects.";
    }
}