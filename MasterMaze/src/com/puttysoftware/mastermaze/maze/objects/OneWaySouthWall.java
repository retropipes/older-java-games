/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class OneWaySouthWall extends GenericWall {
    public OneWaySouthWall() {
        super(true, false, true, true, true, false, true, true,
                ColorConstants.COLOR_BROWN,
                ObjectImageConstants.OBJECT_IMAGE_ONE_WAY_SOUTH,
                ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "One-Way South Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way South Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way South Walls allow movement through them only South.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}
