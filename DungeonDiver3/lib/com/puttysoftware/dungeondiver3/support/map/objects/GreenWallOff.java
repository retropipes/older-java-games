/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class GreenWallOff extends GenericToggleWall {
    // Constructors
    public GreenWallOff() {
        super(false, new TemplateTransform(0.0, 1.0, 0.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Green Walls Off";
    }

    @Override
    public String getDescription() {
        return "Green Walls Off can be walked through, and will change to Green Walls On when a Green Button is pressed.";
    }
}