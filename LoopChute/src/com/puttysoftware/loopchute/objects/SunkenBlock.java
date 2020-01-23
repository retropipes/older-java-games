/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericGround;

public class SunkenBlock extends GenericGround {
    // Constructors
    public SunkenBlock() {
        super(true, true, true, true, ColorConstants.COLOR_WATER);
        this.setAttributeName("block_base");
        this.setAttributeTemplateColor(ColorConstants.COLOR_SUNKEN_BLOCK);
    }

    @Override
    public String getBaseName() {
        return "textured";
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