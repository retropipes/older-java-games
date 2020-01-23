/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class MagentaWallOff extends GenericToggleWall {
    // Constructors
    public MagentaWallOff() {
        super(false, new TemplateTransform(1.0, 0.0, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls Off";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls Off can be walked through, and will change to Magenta Walls On when a Magenta Button is pressed.";
    }
}