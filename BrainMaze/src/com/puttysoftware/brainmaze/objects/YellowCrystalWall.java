/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckpoint;

public class YellowCrystalWall extends GenericCheckpoint {
    // Constructors
    public YellowCrystalWall() {
        super(new YellowCrystal(), ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Yellow Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Yellow Crystal Walls require Yellow Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}