/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class GarnetSquare extends AbstractMultipleKey {
    // Constructors
    public GarnetSquare() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Garnet Square";
    }

    @Override
    public String getPluralName() {
        return "Garnet Squares";
    }

    @Override
    public String getDescription() {
        return "Garnet Squares are the keys to Garnet Walls.";
    }
}