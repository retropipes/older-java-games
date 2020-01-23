/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTeleport;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Destination extends AbstractTeleport {
    // Constructors
    public Destination() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_DESTINATION);
    }

    @Override
    public String getName() {
        return "Destination";
    }

    @Override
    public String getPluralName() {
        return "Destinations";
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        return this;
    }

    @Override
    public String getDescription() {
        return "";
    }
}