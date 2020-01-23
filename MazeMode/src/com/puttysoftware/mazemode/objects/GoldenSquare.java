/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericMultipleKey;

public class GoldenSquare extends GenericMultipleKey {
    // Constructors
    public GoldenSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Golden Square";
    }

    @Override
    public String getPluralName() {
        return "Golden Squares";
    }

    @Override
    public String getDescription() {
        return "Golden Squares are the keys to Golden Walls.";
    }
}