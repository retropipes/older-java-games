/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericProgrammableKey;

public class RoseCrystal extends GenericProgrammableKey {
    // Constructors
    public RoseCrystal() {
        super("Rose", ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Crystal";
    }

    @Override
    public String getPluralName() {
        return "Rose Crystals";
    }

    @Override
    public String getDescription() {
        return "Rose Crystals will open Rose Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}