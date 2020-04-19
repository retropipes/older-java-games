/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTrappedWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class TrappedWall4 extends GenericTrappedWall {
    public TrappedWall4() {
        super(4);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 4 disappear when any Wall Trap 4 is triggered.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.TRAPPED_WALL_4;
    }
}
