/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RedCrystal extends AbstractProgrammableKey {
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