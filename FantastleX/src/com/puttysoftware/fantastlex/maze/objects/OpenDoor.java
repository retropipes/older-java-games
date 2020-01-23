/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractPassThroughObject;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class OpenDoor extends AbstractPassThroughObject {
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
