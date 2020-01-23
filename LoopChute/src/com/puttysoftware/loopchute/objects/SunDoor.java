/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class SunDoor extends GenericCheckpoint {
    // Constructors
    public SunDoor() {
        super(new SunStone());
        this.setTemplateColor(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getBaseName() {
        return "Door";
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sun Door";
    }

    @Override
    public String getPluralName() {
        return "Sun Doors";
    }

    @Override
    public String getDescription() {
        return "Sun Doors will not allow passage without enough Sun Stones.";
    }
}