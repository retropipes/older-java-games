/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericGround;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;

public class Tundra extends GenericGround {
    // Constructors
    public Tundra() {
        super(ColorConstants.COLOR_TUNDRA);
    }

    @Override
    public String getName() {
        return "Tundra";
    }

    @Override
    public String getPluralName() {
        return "Squares of Tundra";
    }

    @Override
    public String getDescription() {
        return "Tundra is one of the many types of ground.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}