/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class YellowWallOff extends GenericToggleWall {
    // Constructors
    public YellowWallOff() {
        super(false, new TemplateTransform(1.0, 1.0, 0.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls Off";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls Off can be walked through, and will change to Yellow Walls On when a Yellow Button is pressed.";
    }
}