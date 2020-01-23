/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractBow;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

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
