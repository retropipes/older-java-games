/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class WhiteCrystal extends AbstractProgrammableKey {
    // Constructors
    public WhiteCrystal() {
        super("White", ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Crystal";
    }

    @Override
    public String getPluralName() {
        return "White Crystals";
    }

    @Override
    public String getDescription() {
        return "White Crystals will open White Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}