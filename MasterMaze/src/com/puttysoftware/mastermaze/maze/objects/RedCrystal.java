/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class RedCrystal extends GenericProgrammableKey {
    // Constructors
    public RedCrystal() {
        super("Red", ColorConstants.COLOR_RED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Crystal";
    }

    @Override
    public String getPluralName() {
        return "Red Crystals";
    }

    @Override
    public String getDescription() {
        return "Red Crystals will open Red Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}