/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericTrappedWall;

public class MasterTrappedWall extends GenericTrappedWall {
    public MasterTrappedWall() {
        super(GenericTrappedWall.NUMBER_MASTER);
    }

    @Override
    public String getDescription() {
        return "Master Trapped Walls disappear when any Wall Trap is triggered.";
    }
}
