/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericMultipleLock;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class RubyWall extends GenericMultipleLock {
    // Constructors
    public RubyWall() {
        super(new RubySquare());
    }

    @Override
    public String getName() {
        return "Ruby Wall";
    }

    @Override
    public String getPluralName() {
        return "Ruby Walls";
    }

    @Override
    public String getDescription() {
        return "Ruby Walls are impassable without enough Ruby Squares.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.RUBY_WALL;
    }
}