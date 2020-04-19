/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTrappedWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class TrappedWall1 extends GenericTrappedWall {
    public TrappedWall1() {
        super(1);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 1 disappear when any Wall Trap 1 is triggered.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.TRAPPED_WALL_1;
    }
}
