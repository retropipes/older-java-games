/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericBoots;

public class AquaBoots extends GenericBoots {
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
