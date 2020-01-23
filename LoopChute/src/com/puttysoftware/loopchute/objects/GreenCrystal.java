/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class GreenCrystal extends GenericCheckKey {
    // Constructors
    public GreenCrystal() {
        super(ColorConstants.COLOR_GREEN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Crystal";
    }

    @Override
    public String getPluralName() {
        return "Green Crystals";
    }

    @Override
    public String getDescription() {
        return "Green Crystals will open Green Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}