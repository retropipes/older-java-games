/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class PurpleKey extends GenericSingleKey {
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