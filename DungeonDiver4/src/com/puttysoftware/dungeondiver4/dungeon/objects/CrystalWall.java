/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableLock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class CrystalWall extends AbstractProgrammableLock {
    // Constructors
    public CrystalWall() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CRYSTAL_WALL;
    }

    @Override
    public String getName() {
        return "Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Crystal Walls require one Crystal to open. The crystal type required may be different from wall to wall.";
    }
}