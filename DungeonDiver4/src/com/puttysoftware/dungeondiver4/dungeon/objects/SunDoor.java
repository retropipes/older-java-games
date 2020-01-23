/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractCheckpoint;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class SunDoor extends AbstractCheckpoint {
    // Constructors
    public SunDoor() {
        super(new SunStone());
        this.setTemplateColor(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
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