/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMarker;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class DungeonNoteObject extends AbstractMarker {
    // Constructors
    public DungeonNoteObject() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MAP_NOTE;
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