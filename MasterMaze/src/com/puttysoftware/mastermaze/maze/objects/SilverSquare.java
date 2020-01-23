/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;

public class SilverSquare extends GenericMultipleKey {
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