/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class SeaweedCrystal extends GenericCheckKey {
    // Constructors
    public SeaweedCrystal() {
        super(ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Crystal";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Crystals";
    }

    @Override
    public String getDescription() {
        return "Seaweed Crystals will open Seaweed Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}