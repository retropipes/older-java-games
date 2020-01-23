/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Bolt extends AbstractMultipleKey {
    // Constructors
    public Bolt() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt";
    }

    @Override
    public String getPluralName() {
        return "Bolts";
    }

    @Override
    public String getDescription() {
        return "Bolts are the keys to Bolt Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal";
    }
}