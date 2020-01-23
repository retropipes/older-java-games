/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class PurpleCrystal extends AbstractProgrammableKey {
    // Constructors
    public PurpleCrystal() {
        super("Purple", ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Crystal";
    }

    @Override
    public String getPluralName() {
        return "Purple Crystals";
    }

    @Override
    public String getDescription() {
        return "Purple Crystals will open Purple Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}