/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractCheckpoint;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class MoonDoor extends AbstractCheckpoint {
    // Constructors
    public MoonDoor() {
        super(new MoonStone());
        this.setTemplateColor(ColorConstants.COLOR_MOON_DOOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
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