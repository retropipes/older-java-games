/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWallTrap;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class MasterWallTrap extends GenericWallTrap {
    public MasterWallTrap() {
        super(GenericWallTrap.NUMBER_MASTER, null);
    }

    @Override
    public String getDescription() {
        return "Master Wall Traps disappear when stepped on, causing all types of Trapped Walls to also disappear.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MASTER_WALL_TRAP;
    }
}
