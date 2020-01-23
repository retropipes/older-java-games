/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class MagentaWallOn extends GenericToggleWall {
    // Constructors
    public MagentaWallOn() {
        super(true, new TemplateTransform(1.0, 0.0, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall On";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls On";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls On can NOT be walked through, and will change to Magenta Walls Off when a Magenta Button is pressed.";
    }
}