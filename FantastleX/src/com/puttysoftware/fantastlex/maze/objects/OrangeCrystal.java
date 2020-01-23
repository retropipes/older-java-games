/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractProgrammableKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class OrangeCrystal extends AbstractProgrammableKey {
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