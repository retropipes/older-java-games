/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class RedWallOff extends GenericToggleWall {
    // Constructors
    public RedWallOff() {
        super(false, new TemplateTransform(1.0, 0.0, 0.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Red Walls Off";
    }

    @Override
    public String getDescription() {
        return "Red Walls Off can be walked through, and will change to Red Walls On when a Red Button is pressed.";
    }
}