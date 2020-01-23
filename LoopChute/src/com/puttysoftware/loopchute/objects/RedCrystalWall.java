/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class RedCrystalWall extends GenericCheckpoint {
    // Constructors
    public RedCrystalWall() {
        super(new RedCrystal(), ColorConstants.COLOR_RED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Red Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Red Crystal Walls require Red Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}