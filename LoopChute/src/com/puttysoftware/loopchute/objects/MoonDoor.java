/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class MoonDoor extends GenericCheckpoint {
    // Constructors
    public MoonDoor() {
        super(new MoonStone());
        this.setTemplateColor(ColorConstants.COLOR_MOON_DOOR);
    }

    @Override
    public String getBaseName() {
        return "Door";
    }

    // Scriptability
    @Override
    public String getName() {
        return "Moon Door";
    }

    @Override
    public String getPluralName() {
        return "Moon Doors";
    }

    @Override
    public String getDescription() {
        return "Moon Doors will not allow passage without enough Moon Stones.";
    }
}