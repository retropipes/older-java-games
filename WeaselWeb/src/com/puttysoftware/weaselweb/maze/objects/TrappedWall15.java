/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericTrappedWall;

public class TrappedWall15 extends GenericTrappedWall {
    public TrappedWall15() {
        super(15);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 15 disappear when any Wall Trap 15 is triggered.";
    }
}
