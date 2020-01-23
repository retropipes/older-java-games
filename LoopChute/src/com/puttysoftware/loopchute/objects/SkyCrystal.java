/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class SkyCrystal extends GenericCheckKey {
    // Constructors
    public SkyCrystal() {
        super(ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Crystal";
    }

    @Override
    public String getPluralName() {
        return "Sky Crystals";
    }

    @Override
    public String getDescription() {
        return "Sky Crystals will open Sky Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}