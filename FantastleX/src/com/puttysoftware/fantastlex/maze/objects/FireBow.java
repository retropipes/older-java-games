/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBow;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class FireBow extends AbstractBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public FireBow() {
        super(FireBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_FIRE,
                ColorConstants.COLOR_LIGHT_RED);
    }

    @Override
    public String getName() {
        return "Fire Bow";
    }

    @Override
    public String getPluralName() {
        return "Fire Bows";
    }

    @Override
    public String getDescription() {
        return "Fire Bows allow shooting of Fire Arrows, which burn Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
