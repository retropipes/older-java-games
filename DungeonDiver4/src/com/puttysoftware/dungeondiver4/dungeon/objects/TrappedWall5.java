/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrappedWall;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class TrappedWall5 extends AbstractTrappedWall {
    public TrappedWall5() {
        super(5);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 5 disappear when any Wall Trap 5 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_5;
    }
}
