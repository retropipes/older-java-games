/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class CyanCrystalWall extends GenericCheckpoint {
    // Constructors
    public CyanCrystalWall() {
        super(new CyanCrystal(), ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Cyan Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Cyan Crystal Walls require Cyan Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}