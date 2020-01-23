/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Suit extends AbstractMultipleKey {
    // Constructors
    public Suit() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Suit";
    }

    @Override
    public String getPluralName() {
        return "Suits";
    }

    @Override
    public String getDescription() {
        return "Suits are the keys to Suit Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Magenta Crystal";
    }
}