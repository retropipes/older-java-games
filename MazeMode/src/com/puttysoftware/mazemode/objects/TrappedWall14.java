/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericTrappedWall;

public class TrappedWall14 extends GenericTrappedWall {
    public TrappedWall14() {
        super(14);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 14 disappear when any Wall Trap 14 is triggered.";
    }
}
