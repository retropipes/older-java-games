/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SeaweedCrystal extends AbstractProgrammableKey {
    // Constructors
    public SeaweedCrystal() {
        super("Seaweed", ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Crystal";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Crystals";
    }

    @Override
    public String getDescription() {
        return "Seaweed Crystals will open Seaweed Crystal Walls.";
    }

    @Override
    public final String getIdentifierV1() {
        return "";
    }
}