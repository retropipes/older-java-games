/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericWallTrap;

public class WallTrap9 extends GenericWallTrap {
    public WallTrap9() {
        super(9, new TrappedWall9());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 9 disappear when stepped on, causing all Trapped Walls 9 to also disappear.";
    }
}
