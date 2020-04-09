/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericWallTrap;

public class WallTrap18 extends GenericWallTrap {
    public WallTrap18() {
        super(18, new TrappedWall18());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 18 disappear when stepped on, causing all Trapped Walls 18 to also disappear.";
    }
}
