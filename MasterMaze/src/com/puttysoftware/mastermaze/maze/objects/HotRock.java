/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericGround;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;

public class HotRock extends GenericGround {
    // Constructors
    public HotRock() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Hot Rock";
    }

    @Override
    public String getPluralName() {
        return "Squares of Hot Rock";
    }

    @Override
    public String getDescription() {
        return "Hot Rock is one of the many types of ground. It is created by Fire Amulets and Hot Boots, but can also exist on its own.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}