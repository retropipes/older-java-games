/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBarrier;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

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