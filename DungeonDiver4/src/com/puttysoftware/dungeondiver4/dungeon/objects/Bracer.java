/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMultipleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class Bracer extends AbstractMultipleKey {
    // Constructors
    public Bracer() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BRACERS;
    }

    @Override
    public String getName() {
        return "Bracer";
    }

    @Override
    public String getPluralName() {
        return "Bracers";
    }

    @Override
    public String getDescription() {
        return "Bracers are the keys to Bracer Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Cyan Crystal";
    }
}