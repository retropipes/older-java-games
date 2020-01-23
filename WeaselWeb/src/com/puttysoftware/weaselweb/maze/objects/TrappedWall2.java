/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericTrappedWall;

public class TrappedWall2 extends GenericTrappedWall {
    public TrappedWall2() {
        super(2);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 2 disappear when any Wall Trap 2 is triggered.";
    }
}
