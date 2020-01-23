/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractCheckpoint;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SunDoor extends AbstractCheckpoint {
    // Constructors
    public SunDoor() {
        super(new SunStone());
        this.setTemplateColor(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sun Door";
    }

    @Override
    public String getPluralName() {
        return "Sun Doors";
    }

    @Override
    public String getDescription() {
        return "Sun Doors will not allow passage without enough Sun Stones.";
    }
}