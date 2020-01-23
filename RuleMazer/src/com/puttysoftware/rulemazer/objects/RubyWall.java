/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericMultipleLock;

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
}