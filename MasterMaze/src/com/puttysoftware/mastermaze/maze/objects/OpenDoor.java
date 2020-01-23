/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericPassThroughObject;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class OpenDoor extends GenericPassThroughObject {
    // Constructors
    public OpenDoor() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_DOOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_OPEN_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Open Door";
    }

    @Override
    public String getPluralName() {
        return "Open Doors";
    }

    @Override
    public String getDescription() {
        return "Open Doors are purely decorative.";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
