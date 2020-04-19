/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWallTrap;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class WallTrap7 extends GenericWallTrap {
    public WallTrap7() {
        super(7, new TrappedWall7());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 7 disappear when stepped on, causing all Trapped Walls 7 to also disappear.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.WALL_TRAP_7;
    }
}
