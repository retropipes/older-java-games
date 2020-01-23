/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractSingleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class Key extends AbstractSingleKey {
    // Constructors
    public Key() {
        super(ColorConstants.COLOR_BLACK);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public String getPluralName() {
        return "Keys";
    }

    @Override
    public String getDescription() {
        return "Keys unlock Locks, and can only be used once.";
    }
}