/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBarrier;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class HorizontalBarrier extends AbstractBarrier {
    // Constructors
    public HorizontalBarrier() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HORIZONTAL_BARRIER;
    }

    @Override
    public String getName() {
        return "Horizontal Barrier";
    }

    @Override
    public String getPluralName() {
        return "Horizontal Barriers";
    }

    @Override
    public String getDescription() {
        return "Horizontal Barriers are impassable - you'll need to go around them.";
    }
}