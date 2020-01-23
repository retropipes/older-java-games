/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class TreasureKey extends AbstractSingleKey {
    // Constructors
    public TreasureKey() {
        super(ColorConstants.COLOR_BRIDGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Treasure Key";
    }

    @Override
    public String getPluralName() {
        return "Treasure Keys";
    }

    @Override
    public String getDescription() {
        return "Treasure Keys unlock Treasure Chests, and can only be used once.";
    }

    @Override
    public final String getIdentifierV1() {
        return "Bow";
    }
}