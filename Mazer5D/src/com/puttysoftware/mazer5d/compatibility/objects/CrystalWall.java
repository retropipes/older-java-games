/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericProgrammableLock;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class CrystalWall extends GenericProgrammableLock {
    // Constructors
    public CrystalWall() {
        super();
    }

    @Override
    public String getName() {
        return "Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Crystal Walls require one Crystal to open. The crystal type required may be different from wall to wall.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.CRYSTAL_WALL;
    }}