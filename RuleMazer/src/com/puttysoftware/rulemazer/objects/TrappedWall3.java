/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericTrappedWall;

public class TrappedWall3 extends GenericTrappedWall {
    public TrappedWall3() {
        super(3);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 3 disappear when any Wall Trap 3 is triggered.";
    }
}
