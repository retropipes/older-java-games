/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

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