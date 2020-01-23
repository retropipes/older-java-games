/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckpoint;

public class SkyCrystalWall extends GenericCheckpoint {
    // Constructors
    public SkyCrystalWall() {
        super(new SkyCrystal(), ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Sky Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Sky Crystal Walls require Sky Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}