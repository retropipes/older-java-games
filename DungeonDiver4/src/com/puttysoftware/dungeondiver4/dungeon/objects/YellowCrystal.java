/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class YellowCrystal extends AbstractProgrammableKey {
    // Constructors
    public YellowCrystal() {
        super("Yellow", ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Crystal";
    }

    @Override
    public String getPluralName() {
        return "Yellow Crystals";
    }

    @Override
    public String getDescription() {
        return "Yellow Crystals will open Yellow Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}