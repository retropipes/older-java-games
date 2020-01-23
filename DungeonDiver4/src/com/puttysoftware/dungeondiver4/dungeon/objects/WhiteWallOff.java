/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractToggleWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class WhiteWallOff extends AbstractToggleWall {
    // Constructors
    public WhiteWallOff() {
        super(false, ColorConstants.COLOR_WHITE);
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