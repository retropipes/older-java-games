/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericSingleKey;

public class MetalKey extends GenericSingleKey {
    // Constructors
    public MetalKey() {
        super(ColorConstants.COLOR_GRAY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Metal Key";
    }

    @Override
    public String getPluralName() {
        return "Metal Keys";
    }

    @Override
    public String getDescription() {
        return "Metal Keys will open Metal Doors, and can only be used once.";
    }
}