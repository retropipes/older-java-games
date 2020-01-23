/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Cap extends AbstractMultipleKey {
    // Constructors
    public Cap() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CAP;
    }

    @Override
    public String getName() {
        return "Cap";
    }

    @Override
    public String getPluralName() {
        return "Caps";
    }

    @Override
    public String getDescription() {
        return "Caps are the keys to Cap Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Red Crystal";
    }
}