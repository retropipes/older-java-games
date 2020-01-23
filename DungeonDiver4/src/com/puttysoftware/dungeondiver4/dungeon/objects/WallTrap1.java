/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWallTrap;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class WallTrap1 extends AbstractWallTrap {
    public WallTrap1() {
        super(1, new TrappedWall1());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 1 disappear when stepped on, causing all Trapped Walls 1 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_1;
    }
}
