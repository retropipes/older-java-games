/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Explore extends AbstractMultipleKey {
    // Constructors
    public Explore() {
        super(ColorConstants.COLOR_SEAWEED);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXPLORE;
    }

    @Override
    public String getName() {
        return "Explore";
    }

    @Override
    public String getPluralName() {
        return "Explores";
    }

    @Override
    public String getDescription() {
        return "Explores are the keys to Explore Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Seaweed Crystal";
    }
}