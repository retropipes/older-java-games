/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckpoint;

public class PurpleCrystalWall extends GenericCheckpoint {
    // Constructors
    public PurpleCrystalWall() {
        super(new PurpleCrystal(), ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Purple Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Purple Crystal Walls require Purple Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}