/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractWallTrap;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class WallTrap9 extends AbstractWallTrap {
    public WallTrap9() {
        super(9, new TrappedWall9());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 9 disappear when stepped on, causing all Trapped Walls 9 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_9;
    }
}
