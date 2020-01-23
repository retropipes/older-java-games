/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class MagentaCrystal extends GenericCheckKey {
    // Constructors
    public MagentaCrystal() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Crystal";
    }

    @Override
    public String getPluralName() {
        return "Magenta Crystals";
    }

    @Override
    public String getDescription() {
        return "Magenta Crystals will open Magenta Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}