/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class PasswallBoots extends AbstractBoots {
    // Constructors
    public PasswallBoots() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Passwall Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Passwall Boots";
    }

    @Override
    public String getDescription() {
        return "Passwall Boots allow you to pass through most walls as you walk. Note that you can only wear one pair of boots at once.";
    }
}
