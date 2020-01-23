/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class MagentaKey extends AbstractSingleKey {
    // Constructors
    public MagentaKey() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Key";
    }

    @Override
    public String getPluralName() {
        return "Magenta Keys";
    }

    @Override
    public String getDescription() {
        return "Magenta Keys will unlock Magenta Locks, and can only be used once.";
    }
}