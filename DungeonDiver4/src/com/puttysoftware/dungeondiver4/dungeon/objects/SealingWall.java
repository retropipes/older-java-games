/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class SealingWall extends AbstractWall {
    // Constructors
    public SealingWall() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SEALING_WALL;
    }

    @Override
    public String getName() {
        return "Sealing Wall";
    }

    @Override
    public String getPluralName() {
        return "Sealing Walls";
    }

    @Override
    public String getDescription() {
        return "Sealing Walls are impassable and indestructible - you'll need to go around them.";
    }
}