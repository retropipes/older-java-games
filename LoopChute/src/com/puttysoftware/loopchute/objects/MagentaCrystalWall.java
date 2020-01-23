/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class MagentaCrystalWall extends GenericCheckpoint {
    // Constructors
    public MagentaCrystalWall() {
        super(new MagentaCrystal(), ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Magenta Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Magenta Crystal Walls require Magenta Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}