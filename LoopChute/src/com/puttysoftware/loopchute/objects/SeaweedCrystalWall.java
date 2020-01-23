/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class SeaweedCrystalWall extends GenericCheckpoint {
    // Constructors
    public SeaweedCrystalWall() {
        super(new SeaweedCrystal(), ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Crystal Wall";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Crystal Walls";
    }

    @Override
    public String getDescription() {
        return "Seaweed Crystal Walls require Seaweed Crystals to open.";
    }

    @Override
    public String getBaseName() {
        return "Crystal Wall";
    }
}