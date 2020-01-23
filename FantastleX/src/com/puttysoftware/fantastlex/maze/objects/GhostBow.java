/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBow;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class GhostBow extends AbstractBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public GhostBow() {
        super(GhostBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_GHOST,
                ColorConstants.COLOR_WHITE);
    }

    @Override
    public String getName() {
        return "Ghost Bow";
    }

    @Override
    public String getPluralName() {
        return "Ghost Bows";
    }

    @Override
    public String getDescription() {
        return "Ghost Bows allow shooting of Ghost Arrows, which pass through objects that do not react to arrows, even if they are solid, and do everything normal arrows do.";
    }
}
