/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractMarker;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;

public class DungeonNoteObject extends AbstractMarker {
    // Constructors
    public DungeonNoteObject() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_NOTE;
    }

    @Override
    public String getName() {
	return "Dungeon Note";
    }

    @Override
    public String getPluralName() {
	return "Dungeon Notes";
    }

    @Override
    public String getDescription() {
	return "";
    }
}