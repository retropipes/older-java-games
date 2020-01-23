/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractBoots;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class MetalBoots extends AbstractBoots {
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
