/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrappedWall;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class TrappedWall4 extends AbstractTrappedWall {
    public TrappedWall4() {
        super(4);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 4 disappear when any Wall Trap 4 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_4;
    }
}
