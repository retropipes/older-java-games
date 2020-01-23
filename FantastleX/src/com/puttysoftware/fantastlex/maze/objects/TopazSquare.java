/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class TopazSquare extends AbstractMultipleKey {
    // Constructors
    public TopazSquare() {
        super(ColorConstants.COLOR_SKY);
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