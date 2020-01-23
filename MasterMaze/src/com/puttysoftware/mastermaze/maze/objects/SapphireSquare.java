/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;

public class SapphireSquare extends GenericMultipleKey {
    // Constructors
    public SapphireSquare() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Sapphire Square";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Squares";
    }

    @Override
    public String getDescription() {
        return "Sapphire Squares are the keys to Sapphire Walls.";
    }
}