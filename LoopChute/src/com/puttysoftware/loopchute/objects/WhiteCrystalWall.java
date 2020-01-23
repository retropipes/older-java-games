/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class WhiteCrystalWall extends GenericCheckpoint {
    // Constructors
    public WhiteCrystalWall() {
        super(new WhiteCrystal(), ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "White Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "White Crystal Walls require White Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}