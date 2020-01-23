/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class WhiteCrystal extends GenericCheckKey {
    // Constructors
    public WhiteCrystal() {
        super(ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "White Crystal";
    }

    @Override
    public String getPluralName() {
        return "White Crystals";
    }

    @Override
    public String getDescription() {
        return "White Crystals will open White Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}