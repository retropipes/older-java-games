/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class WhiteCrystal extends GenericProgrammableKey {
    // Constructors
    public WhiteCrystal() {
        super("White", ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Crystal";
    }

    @Override
    public String getPluralName() {
        return "White Crystals";
    }

    @Override
    public String getDescription() {
        return "White Crystals will open White Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}