/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericWallTrap;

public class WallTrap8 extends GenericWallTrap {
    public WallTrap8() {
        super(8, new TrappedWall8());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 8 disappear when stepped on, causing all Trapped Walls 8 to also disappear.";
    }
}
