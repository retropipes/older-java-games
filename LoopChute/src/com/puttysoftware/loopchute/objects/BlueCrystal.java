/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class BlueCrystal extends GenericCheckKey {
    // Constructors
    public BlueCrystal() {
        super(ColorConstants.COLOR_BLUE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Crystal";
    }

    @Override
    public String getPluralName() {
        return "Blue Crystals";
    }

    @Override
    public String getDescription() {
        return "Blue Crystals will open Blue Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}