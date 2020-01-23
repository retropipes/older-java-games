/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractTrappedWall;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class TrappedWall6 extends AbstractTrappedWall {
    public TrappedWall6() {
        super(6);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 6 disappear when any Wall Trap 6 is triggered.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_6;
    }
}
