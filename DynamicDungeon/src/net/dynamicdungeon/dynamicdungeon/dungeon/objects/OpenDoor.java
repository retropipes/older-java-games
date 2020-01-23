/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractPassThroughObject;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;

public class OpenDoor extends AbstractPassThroughObject {
    // Constructors
    public OpenDoor() {
	super();
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
}
