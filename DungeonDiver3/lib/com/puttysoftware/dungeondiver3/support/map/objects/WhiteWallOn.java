/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericToggleWall;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class WhiteWallOn extends GenericToggleWall {
    // Constructors
    public WhiteWallOn() {
        super(true, new TemplateTransform(1.0, 1.0, 1.0));
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Wall On";
    }

    @Override
    public String getPluralName() {
        return "White Walls On";
    }

    @Override
    public String getDescription() {
        return "White Walls On can NOT be walked through, and will change to White Walls Off when a White Button is pressed.";
    }
}