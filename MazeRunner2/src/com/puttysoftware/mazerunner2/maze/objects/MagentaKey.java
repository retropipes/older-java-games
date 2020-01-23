/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class MagentaKey extends AbstractSingleKey {
    // Constructors
    public MagentaKey() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Key";
    }

    @Override
    public String getPluralName() {
        return "Magenta Keys";
    }

    @Override
    public String getDescription() {
        return "Magenta Keys will unlock Magenta Locks, and can only be used once.";
    }
}