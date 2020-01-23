/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class BlueKey extends AbstractSingleKey {
    // Constructors
    public BlueKey() {
        super(ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Key";
    }

    @Override
    public String getPluralName() {
        return "Blue Keys";
    }

    @Override
    public String getDescription() {
        return "Blue Keys will unlock Blue Locks, and can only be used once.";
    }
}