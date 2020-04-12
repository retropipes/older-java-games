/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWallTrap;

public class WallTrap2 extends GenericWallTrap {
    public WallTrap2() {
        super(2, new TrappedWall2());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 2 disappear when stepped on, causing all Trapped Walls 2 to also disappear.";
    }
}
