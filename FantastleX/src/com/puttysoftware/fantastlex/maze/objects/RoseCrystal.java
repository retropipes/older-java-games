/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RoseCrystal extends AbstractProgrammableKey {
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