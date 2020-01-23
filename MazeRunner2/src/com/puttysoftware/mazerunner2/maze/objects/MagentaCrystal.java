/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class MagentaCrystal extends AbstractProgrammableKey {
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