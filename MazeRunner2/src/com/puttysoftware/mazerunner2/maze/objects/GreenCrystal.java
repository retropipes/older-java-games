/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class GreenCrystal extends AbstractProgrammableKey {
    // Constructors
    public GreenCrystal() {
        super("Green", ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Crystal";
    }

    @Override
    public String getPluralName() {
        return "Green Crystals";
    }

    @Override
    public String getDescription() {
        return "Green Crystals will open Green Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}