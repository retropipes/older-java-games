/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class SkyCrystal extends GenericProgrammableKey {
    // Constructors
    public SkyCrystal() {
        super("Sky", ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Crystal";
    }

    @Override
    public String getPluralName() {
        return "Sky Crystals";
    }

    @Override
    public String getDescription() {
        return "Sky Crystals will open Sky Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}