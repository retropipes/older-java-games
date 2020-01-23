/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class SkyWallOn extends GenericToggleWall {
    // Constructors
    public SkyWallOn() {
        super(true, new TemplateTransform(0.5, 0.5, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Wall On";
    }

    @Override
    public String getPluralName() {
        return "Sky Walls On";
    }

    @Override
    public String getDescription() {
        return "Sky Walls On can NOT be walked through, and will change to Sky Walls Off when a Sky Button is pressed.";
    }
}