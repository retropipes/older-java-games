/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractSingleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class YellowKey extends AbstractSingleKey {
    // Constructors
    public YellowKey() {
        super(ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Key";
    }

    @Override
    public String getPluralName() {
        return "Yellow Keys";
    }

    @Override
    public String getDescription() {
        return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}