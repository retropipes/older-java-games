/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericBoots;

public class PasswallBoots extends GenericBoots {
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
