/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBow;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

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
