/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RegularBoots extends AbstractBoots {
    // Constructors
    public RegularBoots() {
        super(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Regular Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Regular Boots";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
