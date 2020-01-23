/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class YellowWallOn extends GenericToggleWall {
    // Constructors
    public YellowWallOn() {
        super(true, new TemplateTransform(1.0, 1.0, 0.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall On";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls On";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls On can NOT be walked through, and will change to Yellow Walls Off when a Yellow Button is pressed.";
    }
}