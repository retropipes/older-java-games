/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrappedWall;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class MasterTrappedWall extends AbstractTrappedWall {
    public MasterTrappedWall() {
        super(AbstractTrappedWall.NUMBER_MASTER);
    }

    @Override
    public String getDescription() {
        return "Master Trapped Walls disappear when any Wall Trap is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_MASTER;
    }
}
