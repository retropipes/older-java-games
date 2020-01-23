/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SapphireWall extends AbstractMultipleLock {
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