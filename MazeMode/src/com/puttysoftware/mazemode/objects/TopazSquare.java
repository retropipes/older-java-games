/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericMultipleKey;

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
}