/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class OneWayWestWall extends AbstractWall {
    public OneWayWestWall() {
        super(true, true, true, false, true, true, true, false,
                ColorConstants.COLOR_BROWN,
                ObjectImageConstants.OBJECT_IMAGE_ONE_WAY_WEST,
                ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "One-Way West Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way West Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way West Walls allow movement through them only West.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}
