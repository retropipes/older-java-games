/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class WhiteWallOff extends GenericToggleWall {
    // Constructors
    public WhiteWallOff() {
        super(false, new TemplateTransform(1.0, 1.0, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Wall Off";
    }

    @Override
    public String getPluralName() {
        return "White Walls Off";
    }

    @Override
    public String getDescription() {
        return "White Walls Off can be walked through, and will change to White Walls On when a White Button is pressed.";
    }
}