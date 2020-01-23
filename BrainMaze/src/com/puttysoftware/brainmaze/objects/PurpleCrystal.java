/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckKey;

public class PurpleCrystal extends GenericCheckKey {
    // Constructors
    public PurpleCrystal() {
        super(ColorConstants.COLOR_PURPLE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Crystal";
    }

    @Override
    public String getPluralName() {
        return "Purple Crystals";
    }

    @Override
    public String getDescription() {
        return "Purple Crystals will open Purple Crystal Walls.";
    }

    @Override
    public String getBaseName() {
        return "Crystal";
    }
}