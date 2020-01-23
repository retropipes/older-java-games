/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class RubySquare extends GenericCheckKey {
    // Constructors
    public RubySquare() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getBaseName() {
        return "square";
    }

    @Override
    public String getName() {
        return "Ruby Square";
    }

    @Override
    public String getPluralName() {
        return "Ruby Squares";
    }

    @Override
    public String getDescription() {
        return "Ruby Squares are the keys to Ruby Walls.";
    }
}