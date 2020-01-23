/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class BlueWallOff extends GenericToggleWall {
    // Constructors
    public BlueWallOff() {
        super(false, new TemplateTransform(0.0, 0.0, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls Off";
    }

    @Override
    public String getDescription() {
        return "Blue Walls Off can be walked through, and will change to Blue Walls On when a Blue Button is pressed.";
    }
}