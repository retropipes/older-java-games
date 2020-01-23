/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractWallTrap;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class WallTrap3 extends AbstractWallTrap {
    public WallTrap3() {
        super(3, new TrappedWall3());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 3 disappear when stepped on, causing all Trapped Walls 3 to also disappear.";
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SMALL_3;
    }
}
