/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class YellowCrystal extends GenericCheckKey {
    // Constructors
    public YellowCrystal() {
        super(ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Crystal";
    }

    @Override
    public String getPluralName() {
        return "Yellow Crystals";
    }

    @Override
    public String getDescription() {
        return "Yellow Crystals will open Yellow Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}