/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckKey;

public class CyanCrystal extends GenericCheckKey {
    // Constructors
    public CyanCrystal() {
        super(ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Crystal";
    }

    @Override
    public String getPluralName() {
        return "Cyan Crystals";
    }

    @Override
    public String getDescription() {
        return "Cyan Crystals will open Cyan Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}