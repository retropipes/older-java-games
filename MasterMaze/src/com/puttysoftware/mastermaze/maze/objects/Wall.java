/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;

public class Wall extends GenericWall {
    // Constructors
    public Wall() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Walls";
    }

    @Override
    public String getDescription() {
        return "Walls are impassable - you'll need to go around them.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}