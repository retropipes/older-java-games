/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericGround;

public class SunkenBlock extends GenericGround {
    // Constructors
    public SunkenBlock() {
        super(true, true, true, true, ColorConstants.COLOR_NONE);
    }

    @Override
    public String getBaseName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "Sunken Block";
    }

    @Override
    public String getPluralName() {
        return "Sunken Blocks";
    }

    @Override
    public String getDescription() {
        return "Sunken Blocks are created when Pushable Blocks are pushed into Water, and behave just like Tiles.";
    }
}