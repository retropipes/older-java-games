/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SilverSquare extends AbstractMultipleKey {
    // Constructors
    public SilverSquare() {
        super(ColorConstants.COLOR_WHITE);
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
}