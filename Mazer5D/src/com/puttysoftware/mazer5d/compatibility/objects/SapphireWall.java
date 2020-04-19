/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleLock;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SapphireWall extends GenericMultipleLock {
    // Constructors
    public SapphireWall() {
        super(new SapphireSquare());
    }

    @Override
    public String getName() {
        return "Sapphire Wall";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Walls";
    }

    @Override
    public String getDescription() {
        return "Sapphire Walls are impassable without enough Sapphire Squares.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SAPPHIRE_WALL;
    }
}