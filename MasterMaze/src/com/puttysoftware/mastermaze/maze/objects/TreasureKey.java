/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class TreasureKey extends GenericSingleKey {
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