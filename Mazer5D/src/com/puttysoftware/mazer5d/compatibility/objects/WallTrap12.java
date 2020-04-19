/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWallTrap;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class WallTrap12 extends GenericWallTrap {
    public WallTrap12() {
        super(12, new TrappedWall12());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 12 disappear when stepped on, causing all Trapped Walls 12 to also disappear.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.WALL_TRAP_12;
    }
}
