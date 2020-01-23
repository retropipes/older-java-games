/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericTrappedWall;

public class TrappedWall8 extends GenericTrappedWall {
    public TrappedWall8() {
        super(8);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 8 disappear when any Wall Trap 8 is triggered.";
    }
}
