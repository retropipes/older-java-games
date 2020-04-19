/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class GarnetSquare extends GenericMultipleKey {
    // Constructors
    public GarnetSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Garnet Square";
    }

    @Override
    public String getPluralName() {
        return "Garnet Squares";
    }

    @Override
    public String getDescription() {
        return "Garnet Squares are the keys to Garnet Walls.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.GARNET_SQUARE;
    }
}