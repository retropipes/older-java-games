/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractProgrammableKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class RoseCrystal extends AbstractProgrammableKey {
    // Constructors
    public RoseCrystal() {
        super("Rose", ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Crystal";
    }

    @Override
    public String getPluralName() {
        return "Rose Crystals";
    }

    @Override
    public String getDescription() {
        return "Rose Crystals will open Rose Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}