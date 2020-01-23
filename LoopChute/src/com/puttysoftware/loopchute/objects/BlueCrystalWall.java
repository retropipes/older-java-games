/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class BlueCrystalWall extends GenericCheckpoint {
    // Constructors
    public BlueCrystalWall() {
        super(new BlueCrystal(), ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Blue Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Blue Crystal Walls require Blue Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}