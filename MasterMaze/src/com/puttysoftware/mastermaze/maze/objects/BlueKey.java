/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class BlueKey extends GenericSingleKey {
    // Constructors
    public BlueKey() {
        super(ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Key";
    }

    @Override
    public String getPluralName() {
        return "Blue Keys";
    }

    @Override
    public String getDescription() {
        return "Blue Keys will unlock Blue Locks, and can only be used once.";
    }
}