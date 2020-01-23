/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Dagger extends AbstractMultipleKey {
    // Constructors
    public Dagger() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger";
    }

    @Override
    public String getPluralName() {
        return "Daggers";
    }

    @Override
    public String getDescription() {
        return "Daggers are the keys to Dagger Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal";
    }
}