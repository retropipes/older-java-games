/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class OneWayNorthWall extends GenericWall {
    public OneWayNorthWall() {
        super(false, true, true, true, false, true, true, true,
                ColorConstants.COLOR_BROWN,
                ObjectImageConstants.OBJECT_IMAGE_ONE_WAY_NORTH,
                ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "One-Way North Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way North Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way North Walls allow movement through them only North.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}
