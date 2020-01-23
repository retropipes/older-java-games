/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericGround;

public class SunkenBlock extends GenericGround {
    // Constructors
    public SunkenBlock() {
        super(true, true, true, true);
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