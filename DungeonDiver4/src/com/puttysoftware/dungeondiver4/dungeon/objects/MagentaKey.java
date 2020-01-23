/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractSingleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class MagentaKey extends AbstractSingleKey {
    // Constructors
    public MagentaKey() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Key";
    }

    @Override
    public String getPluralName() {
        return "Magenta Keys";
    }

    @Override
    public String getDescription() {
        return "Magenta Keys will unlock Magenta Locks, and can only be used once.";
    }
}