/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class CyanCrystal extends AbstractProgrammableKey {
    // Constructors
    public CyanCrystal() {
        super("Cyan", ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Crystal";
    }

    @Override
    public String getPluralName() {
        return "Cyan Crystals";
    }

    @Override
    public String getDescription() {
        return "Cyan Crystals will open Cyan Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}