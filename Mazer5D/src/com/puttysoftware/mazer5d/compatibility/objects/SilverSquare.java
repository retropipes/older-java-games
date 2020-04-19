/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SilverSquare extends GenericMultipleKey {
    // Constructors
    public SilverSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Silver Square";
    }

    @Override
    public String getPluralName() {
        return "Silver Squares";
    }

    @Override
    public String getDescription() {
        return "Silver Squares are the keys to Silver Walls.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SILVER_SQUARE;
    }
}