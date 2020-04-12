/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTrappedWall;

public class TrappedWall0 extends GenericTrappedWall {
    public TrappedWall0() {
        super(0);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 0 disappear when any Wall Trap 0 is triggered.";
    }
}
