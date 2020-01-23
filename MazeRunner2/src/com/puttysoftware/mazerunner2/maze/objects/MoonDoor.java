/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractCheckpoint;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class MoonDoor extends AbstractCheckpoint {
    // Constructors
    public MoonDoor() {
        super(new MoonStone());
        this.setTemplateColor(ColorConstants.COLOR_MOON_DOOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Moon Door";
    }

    @Override
    public String getPluralName() {
        return "Moon Doors";
    }

    @Override
    public String getDescription() {
        return "Moon Doors will not allow passage without enough Moon Stones.";
    }
}