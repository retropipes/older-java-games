/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckpoint;

public class RoseCrystalWall extends GenericCheckpoint {
    // Constructors
    public RoseCrystalWall() {
        super(new RoseCrystal(), ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Rose Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Rose Crystal Walls require Rose Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}