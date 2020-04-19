/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SealingWall extends GenericWall {
    // Constructors
    public SealingWall() {
        super();
    }

    @Override
    public String getName() {
        return "Sealing Wall";
    }

    @Override
    public String getPluralName() {
        return "Sealing Walls";
    }

    @Override
    public String getDescription() {
        return "Sealing Walls are impassable and impossible to destroy.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SEALING_WALL;
    }}