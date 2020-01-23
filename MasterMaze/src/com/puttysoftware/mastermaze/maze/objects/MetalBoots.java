/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericBoots;

public class MetalBoots extends GenericBoots {
    // Constructors
    public MetalBoots() {
        super(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Metal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Metal Boots";
    }

    @Override
    public String getDescription() {
        return "Metal Boots allow Metal Buttons to be triggered. Note that you can only wear one pair of boots at once.";
    }
}
