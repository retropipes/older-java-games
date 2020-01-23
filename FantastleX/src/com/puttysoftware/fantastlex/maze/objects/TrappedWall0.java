/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractTrappedWall;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class TrappedWall0 extends AbstractTrappedWall {
    public TrappedWall0() {
        super(0);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 0 disappear when any Wall Trap 0 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_0;
    }
}
