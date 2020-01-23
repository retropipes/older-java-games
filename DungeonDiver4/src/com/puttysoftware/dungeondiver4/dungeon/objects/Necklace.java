/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Necklace extends AbstractMultipleKey {
    // Constructors
    public Necklace() {
        super(ColorConstants.COLOR_ROSE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NECKLACE;
    }

    @Override
    public String getName() {
        return "Necklace";
    }

    @Override
    public String getPluralName() {
        return "Necklaces";
    }

    @Override
    public String getDescription() {
        return "Necklaces are the keys to Necklace Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Rose Crystal";
    }
}