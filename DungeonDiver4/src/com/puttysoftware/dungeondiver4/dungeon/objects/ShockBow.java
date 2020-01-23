/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBow;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class ShockBow extends AbstractBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public ShockBow() {
        super(ShockBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_SHOCK,
                ColorConstants.COLOR_LIGHT_YELLOW);
    }

    @Override
    public String getName() {
        return "Shock Bow";
    }

    @Override
    public String getPluralName() {
        return "Shock Bows";
    }

    @Override
    public String getDescription() {
        return "Shock Bows allow shooting of Shock Arrows, which energize Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
