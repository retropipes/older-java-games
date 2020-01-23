/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class MagentaCrystal extends GenericProgrammableKey {
    // Constructors
    public MagentaCrystal() {
        super("Magenta", ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Crystal";
    }

    @Override
    public String getPluralName() {
        return "Magenta Crystals";
    }

    @Override
    public String getDescription() {
        return "Magenta Crystals will open Magenta Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}