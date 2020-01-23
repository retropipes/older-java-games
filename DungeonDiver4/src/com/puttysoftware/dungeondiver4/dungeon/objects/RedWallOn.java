/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractToggleWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class RedWallOn extends AbstractToggleWall {
    // Constructors
    public RedWallOn() {
        super(true, ColorConstants.COLOR_RED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall On";
    }

    @Override
    public String getPluralName() {
        return "Red Walls On";
    }

    @Override
    public String getDescription() {
        return "Red Walls On can NOT be walked through, and will change to Red Walls Off when a Red Button is pressed.";
    }
}