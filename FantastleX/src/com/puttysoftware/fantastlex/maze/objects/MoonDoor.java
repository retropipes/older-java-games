/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractCheckpoint;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

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