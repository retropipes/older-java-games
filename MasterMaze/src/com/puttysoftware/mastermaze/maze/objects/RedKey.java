/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class RedKey extends GenericSingleKey {
    // Constructors
    public RedKey() {
        super(ColorConstants.COLOR_RED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Key";
    }

    @Override
    public String getPluralName() {
        return "Red Keys";
    }

    @Override
    public String getDescription() {
        return "Red Keys will unlock Red Locks, and can only be used once.";
    }
}