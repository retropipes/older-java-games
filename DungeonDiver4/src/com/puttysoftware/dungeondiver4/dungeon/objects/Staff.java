/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Staff extends AbstractMultipleKey {
    // Constructors
    public Staff() {
        super(ColorConstants.COLOR_PURPLE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAFF;
    }

    @Override
    public String getName() {
        return "Staff";
    }

    @Override
    public String getPluralName() {
        return "Staves";
    }

    @Override
    public String getDescription() {
        return "Staves are the keys to Staff Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Purple Crystal";
    }
}