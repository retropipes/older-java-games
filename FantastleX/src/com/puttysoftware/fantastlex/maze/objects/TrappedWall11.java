/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractTrappedWall;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class TrappedWall11 extends AbstractTrappedWall {
    public TrappedWall11() {
        super(11);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 11 disappear when any Wall Trap 11 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_11;
    }
}
