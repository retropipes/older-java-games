/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Shield extends AbstractMultipleKey {
    // Constructors
    public Shield() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SHIELD;
    }

    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public String getPluralName() {
        return "Shields";
    }

    @Override
    public String getDescription() {
        return "Shields are the keys to Shield Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Orange Crystal";
    }
}