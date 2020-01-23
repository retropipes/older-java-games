/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class GreenKey extends GenericSingleKey {
    // Constructors
    public GreenKey() {
        super(ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Key";
    }

    @Override
    public String getPluralName() {
        return "Green Keys";
    }

    @Override
    public String getDescription() {
        return "Green Keys will unlock Green Locks, and can only be used once.";
    }
}