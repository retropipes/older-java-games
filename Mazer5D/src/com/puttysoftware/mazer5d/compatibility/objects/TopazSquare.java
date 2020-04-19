/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class TopazSquare extends GenericMultipleKey {
    // Constructors
    public TopazSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Topaz Square";
    }

    @Override
    public String getPluralName() {
        return "Topaz Squares";
    }

    @Override
    public String getDescription() {
        return "Topaz Squares are the keys to Topaz Walls.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.TOPAZ_SQUARE;
    }
}