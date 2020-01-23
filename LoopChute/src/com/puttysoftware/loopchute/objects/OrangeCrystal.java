/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class OrangeCrystal extends GenericCheckKey {
    // Constructors
    public OrangeCrystal() {
        super(ColorConstants.COLOR_ORANGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Crystal";
    }

    @Override
    public String getPluralName() {
        return "Orange Crystals";
    }

    @Override
    public String getDescription() {
        return "Orange Crystals will open Orange Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}