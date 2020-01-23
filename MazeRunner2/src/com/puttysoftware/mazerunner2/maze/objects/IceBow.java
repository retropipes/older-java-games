/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractBow;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class IceBow extends AbstractBow {
    // Constants
    private static final int BOW_USES = 30;

    // Constructors
    public IceBow() {
        super(IceBow.BOW_USES, ArrowTypeConstants.ARROW_TYPE_ICE,
                ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Ice Bow";
    }

    @Override
    public String getPluralName() {
        return "Ice Bows";
    }

    @Override
    public String getDescription() {
        return "Ice Bows allow shooting of Ice Arrows, which freeze Barrier Generators upon contact, and do everything normal arrows do.";
    }
}
