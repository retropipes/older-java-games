/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class RoseCrystal extends GenericCheckKey {
    // Constructors
    public RoseCrystal() {
        super(ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Crystal";
    }

    @Override
    public String getPluralName() {
        return "Rose Crystals";
    }

    @Override
    public String getDescription() {
        return "Rose Crystals will open Rose Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}