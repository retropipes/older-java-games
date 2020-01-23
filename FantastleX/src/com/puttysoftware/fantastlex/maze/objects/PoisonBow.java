/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBow;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class PoisonBow extends AbstractBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public PoisonBow() {
        super(PoisonBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_POISON,
                ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Poison Bow";
    }

    @Override
    public String getPluralName() {
        return "Poison Bows";
    }

    @Override
    public String getDescription() {
        return "Poison Bows allow shooting of Poison Arrows, which weaken Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
