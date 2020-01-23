/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

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