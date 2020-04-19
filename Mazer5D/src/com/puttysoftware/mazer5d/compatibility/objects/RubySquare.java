/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class RubySquare extends GenericMultipleKey {
    // Constructors
    public RubySquare() {
        super();
    }

    @Override
    public String getName() {
        return "Ruby Square";
    }

    @Override
    public String getPluralName() {
        return "Ruby Squares";
    }

    @Override
    public String getDescription() {
        return "Ruby Squares are the keys to Ruby Walls.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.RUBY_SQUARE;
    }
}