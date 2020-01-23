/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBow;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class Bow extends AbstractBow {
    // Constants
    private static final int BOW_USES = -1;

    // Constructors
    public Bow() {
        super(Bow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_PLAIN,
                ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Bow";
    }

    @Override
    public String getPluralName() {
        return "Bows";
    }

    @Override
    public String getDescription() {
        return "Bows shoot an unlimited supply of normal arrows.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}
