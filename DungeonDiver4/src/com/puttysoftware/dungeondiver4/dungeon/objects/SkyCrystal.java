/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class SkyCrystal extends AbstractProgrammableKey {
    // Constructors
    public SkyCrystal() {
        super("Sky", ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Crystal";
    }

    @Override
    public String getPluralName() {
        return "Sky Crystals";
    }

    @Override
    public String getDescription() {
        return "Sky Crystals will open Sky Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}