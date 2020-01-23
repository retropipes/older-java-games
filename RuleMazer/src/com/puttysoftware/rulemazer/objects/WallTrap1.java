/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericWallTrap;

public class WallTrap1 extends GenericWallTrap {
    public WallTrap1() {
        super(1, new TrappedWall1());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 1 disappear when stepped on, causing all Trapped Walls 1 to also disappear.";
    }
}
