/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractSingleKey;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class MetalKey extends AbstractSingleKey {
    // Constructors
    public MetalKey() {
        super(ColorConstants.COLOR_GRAY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Metal Key";
    }

    @Override
    public String getPluralName() {
        return "Metal Keys";
    }

    @Override
    public String getDescription() {
        return "Metal Keys will open Metal Doors, and can only be used once.";
    }
}