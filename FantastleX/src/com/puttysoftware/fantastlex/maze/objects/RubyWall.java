/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RubyWall extends AbstractMultipleLock {
    // Constructors
    public RubyWall() {
        super(new RubySquare(), ColorConstants.COLOR_RED);
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