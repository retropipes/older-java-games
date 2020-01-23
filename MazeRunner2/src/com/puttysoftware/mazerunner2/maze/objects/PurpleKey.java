/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class PurpleKey extends AbstractSingleKey {
    // Constructors
    public PurpleKey() {
        super(ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Key";
    }

    @Override
    public String getPluralName() {
        return "Purple Keys";
    }

    @Override
    public String getDescription() {
        return "Purple Keys will unlock Purple Locks, and can only be used once.";
    }
}