/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractToggleWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class BlueWallOn extends AbstractToggleWall {
    // Constructors
    public BlueWallOn() {
        super(true, ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall On";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls On";
    }

    @Override
    public String getDescription() {
        return "Blue Walls On can NOT be walked through, and will change to Blue Walls Off when a Blue Button is pressed.";
    }
}