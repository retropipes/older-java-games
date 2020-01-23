/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class OrangeCrystal extends GenericProgrammableKey {
    // Constructors
    public OrangeCrystal() {
        super("Orange", ColorConstants.COLOR_ORANGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Crystal";
    }

    @Override
    public String getPluralName() {
        return "Orange Crystals";
    }

    @Override
    public String getDescription() {
        return "Orange Crystals will open Orange Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}