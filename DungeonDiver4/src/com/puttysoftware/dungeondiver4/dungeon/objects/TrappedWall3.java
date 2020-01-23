/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrappedWall;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class TrappedWall3 extends AbstractTrappedWall {
    public TrappedWall3() {
        super(3);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 3 disappear when any Wall Trap 3 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_3;
    }
}
