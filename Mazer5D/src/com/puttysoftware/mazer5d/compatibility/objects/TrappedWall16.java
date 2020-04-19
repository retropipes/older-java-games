/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTrappedWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class TrappedWall16 extends GenericTrappedWall {
    public TrappedWall16() {
        super(16);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 16 disappear when any Wall Trap 16 is triggered.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.TRAPPED_WALL_16;
    }}
