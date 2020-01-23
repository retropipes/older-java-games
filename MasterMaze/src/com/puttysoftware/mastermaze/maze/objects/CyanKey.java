/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class CyanKey extends GenericSingleKey {
    // Constructors
    public CyanKey() {
        super(ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Key";
    }

    @Override
    public String getPluralName() {
        return "Cyan Keys";
    }

    @Override
    public String getDescription() {
        return "Cyan Keys will unlock Cyan Locks, and can only be used once.";
    }
}