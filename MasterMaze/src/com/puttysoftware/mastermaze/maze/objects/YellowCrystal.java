/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class YellowCrystal extends GenericProgrammableKey {
    // Constructors
    public YellowCrystal() {
        super("Yellow", ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Crystal";
    }

    @Override
    public String getPluralName() {
        return "Yellow Crystals";
    }

    @Override
    public String getDescription() {
        return "Yellow Crystals will open Yellow Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}