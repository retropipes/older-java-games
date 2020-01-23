/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class SeaweedWallOff extends GenericToggleWall {
    // Constructors
    public SeaweedWallOff() {
        super(false, new TemplateTransform(0.5, 1.0, 0.5));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls Off";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls Off can be walked through, and will change to Seaweed Walls On when a Seaweed Button is pressed.";
    }
}