/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class OrangeCrystalWall extends GenericCheckpoint {
    // Constructors
    public OrangeCrystalWall() {
        super(new OrangeCrystal(), ColorConstants.COLOR_ORANGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Orange Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Orange Crystal Walls require Orange Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}