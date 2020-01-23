/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class AquaBoots extends AbstractBoots {
    // Constructors
    public AquaBoots() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public String getName() {
        return "Aqua Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Aqua Boots";
    }

    @Override
    public String getDescription() {
        return "Aqua Boots allow walking on water. Note that you can only wear one pair of boots at once.";
    }
}
