/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RoseKey extends AbstractSingleKey {
    // Constructors
    public RoseKey() {
        super(ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Key";
    }

    @Override
    public String getPluralName() {
        return "Rose Keys";
    }

    @Override
    public String getDescription() {
        return "Rose Keys will unlock Rose Locks, and can only be used once.";
    }
}