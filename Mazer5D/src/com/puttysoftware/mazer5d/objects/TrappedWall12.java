/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericTrappedWall;

public class TrappedWall12 extends GenericTrappedWall {
    public TrappedWall12() {
        super(12);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 12 disappear when any Wall Trap 12 is triggered.";
    }
}
