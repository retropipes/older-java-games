/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Glove extends AbstractMultipleKey {
    // Constructors
    public Glove() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove";
    }

    @Override
    public String getPluralName() {
        return "Gloves";
    }

    @Override
    public String getDescription() {
        return "Gloves are the keys to Glove Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal";
    }
}