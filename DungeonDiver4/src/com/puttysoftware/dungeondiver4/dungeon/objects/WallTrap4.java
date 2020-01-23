/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWallTrap;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class WallTrap4 extends AbstractWallTrap {
    public WallTrap4() {
        super(4, new TrappedWall4());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 4 disappear when stepped on, causing all Trapped Walls 4 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_4;
    }
}
