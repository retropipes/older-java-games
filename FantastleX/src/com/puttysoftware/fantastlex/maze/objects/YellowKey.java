/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class YellowKey extends AbstractSingleKey {
    // Constructors
    public YellowKey() {
        super(ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Key";
    }

    @Override
    public String getPluralName() {
        return "Yellow Keys";
    }

    @Override
    public String getDescription() {
        return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}