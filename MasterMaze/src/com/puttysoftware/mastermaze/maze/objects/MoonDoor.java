/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericCheckpoint;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MoonDoor extends GenericCheckpoint {
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