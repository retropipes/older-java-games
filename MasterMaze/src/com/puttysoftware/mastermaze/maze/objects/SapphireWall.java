/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;

public class SapphireWall extends GenericMultipleLock {
    // Constructors
    public SapphireWall() {
        super(new SapphireSquare(), ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Sapphire Wall";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Walls";
    }

    @Override
    public String getDescription() {
        return "Sapphire Walls are impassable without enough Sapphire Squares.";
    }
}