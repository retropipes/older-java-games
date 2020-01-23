/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericTrappedWall;

public class TrappedWall10 extends GenericTrappedWall {
    public TrappedWall10() {
        super(10);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 10 disappear when any Wall Trap 10 is triggered.";
    }
}
