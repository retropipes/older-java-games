/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericWallTrap;

public class WallTrap4 extends GenericWallTrap {
    public WallTrap4() {
        super(4, new TrappedWall4());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 4 disappear when stepped on, causing all Trapped Walls 4 to also disappear.";
    }
}
