/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericTrappedWall;

public class TrappedWall13 extends GenericTrappedWall {
    public TrappedWall13() {
        super(13);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 13 disappear when any Wall Trap 13 is triggered.";
    }
}
