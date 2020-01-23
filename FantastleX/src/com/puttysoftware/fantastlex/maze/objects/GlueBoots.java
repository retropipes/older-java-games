/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class GlueBoots extends AbstractBoots {
    // Constructors
    public GlueBoots() {
        super(ColorConstants.COLOR_PURPLE);
    }

    @Override
    public String getName() {
        return "Glue Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Glue Boots";
    }

    @Override
    public String getDescription() {
        return "Glue Boots allow walking on Ice without slipping. Note that you can only wear one pair of boots at once.";
    }
}
