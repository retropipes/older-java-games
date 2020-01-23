/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class GreenCrystalWall extends GenericCheckpoint {
    // Constructors
    public GreenCrystalWall() {
        super(new GreenCrystal(), ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Green Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Green Crystal Walls require Green Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}