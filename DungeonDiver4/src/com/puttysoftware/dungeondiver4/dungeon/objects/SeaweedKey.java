/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractSingleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class SeaweedKey extends AbstractSingleKey {
    // Constructors
    public SeaweedKey() {
        super(ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Key";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Keys";
    }

    @Override
    public String getDescription() {
        return "Seaweed Keys will unlock Seaweed Locks, and can only be used once.";
    }
}