/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractGround;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;

public class Snow extends AbstractGround {
    // Constructors
    public Snow() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public String getName() {
        return "Snow";
    }

    @Override
    public String getPluralName() {
        return "Squares of Snow";
    }

    @Override
    public String getDescription() {
        return "Snow is one of the many types of ground.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}