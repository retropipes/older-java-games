/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericWallTrap;

public class MasterWallTrap extends GenericWallTrap {
    public MasterWallTrap() {
        super(GenericWallTrap.NUMBER_MASTER, null);
    }

    @Override
    public String getDescription() {
        return "Master Wall Traps disappear when stepped on, causing all types of Trapped Walls to also disappear.";
    }
}
