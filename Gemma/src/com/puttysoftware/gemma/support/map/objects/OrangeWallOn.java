/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericToggleWall;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class OrangeWallOn extends GenericToggleWall {
    // Constructors
    public OrangeWallOn() {
        super(true, new TemplateTransform(1.0, 0.5, 0.25));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall On";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls On";
    }

    @Override
    public String getDescription() {
        return "Orange Walls On can NOT be walked through, and will change to Orange Walls Off when a Orange Button is pressed.";
    }
}