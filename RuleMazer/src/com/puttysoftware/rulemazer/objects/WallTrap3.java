/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericWallTrap;

public class WallTrap3 extends GenericWallTrap {
    public WallTrap3() {
        super(3, new TrappedWall3());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 3 disappear when stepped on, causing all Trapped Walls 3 to also disappear.";
    }
}
