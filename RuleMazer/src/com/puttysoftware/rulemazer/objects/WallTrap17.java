/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericWallTrap;

public class WallTrap17 extends GenericWallTrap {
    public WallTrap17() {
        super(17, new TrappedWall17());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 17 disappear when stepped on, causing all Trapped Walls 17 to also disappear.";
    }
}
