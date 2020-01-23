/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractSingleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class TreasureKey extends AbstractSingleKey {
    // Constructors
    public TreasureKey() {
        super(ColorConstants.COLOR_BRIDGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Treasure Key";
    }

    @Override
    public String getPluralName() {
        return "Treasure Keys";
    }

    @Override
    public String getDescription() {
        return "Treasure Keys unlock Treasure Chests, and can only be used once.";
    }

    @Override
    public final String getIdentifierV1() {
        return "Bow";
    }
}