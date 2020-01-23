/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleLock;

public class SilverWall extends GenericMultipleLock {
    // Constructors
    public SilverWall() {
        super(new SilverSquare(), ColorConstants.COLOR_WHITE);
    }

    @Override
    public String getName() {
        return "Silver Wall";
    }

    @Override
    public String getPluralName() {
        return "Silver Walls";
    }

    @Override
    public String getDescription() {
        return "Silver Walls are impassable without enough Silver Squares.";
    }
}