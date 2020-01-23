/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class SkyKey extends AbstractSingleKey {
    // Constructors
    public SkyKey() {
        super(ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Key";
    }

    @Override
    public String getPluralName() {
        return "Sky Keys";
    }

    @Override
    public String getDescription() {
        return "Sky Keys will unlock Sky Locks, and can only be used once.";
    }
}