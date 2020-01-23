/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class GoldenWall extends AbstractMultipleLock {
    // Constructors
    public GoldenWall() {
        super(new GoldenSquare(), ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Golden Wall";
    }

    @Override
    public String getPluralName() {
        return "Golden Walls";
    }

    @Override
    public String getDescription() {
        return "Golden Walls are impassable without enough Golden Squares.";
    }
}