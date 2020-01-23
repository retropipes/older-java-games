/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWallTrap;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class WallTrap8 extends AbstractWallTrap {
    public WallTrap8() {
        super(8, new TrappedWall8());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 8 disappear when stepped on, causing all Trapped Walls 8 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_8;
    }
}
