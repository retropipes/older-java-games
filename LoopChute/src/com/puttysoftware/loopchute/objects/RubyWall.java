/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class RubyWall extends GenericCheckpoint {
    // Constructors
    public RubyWall() {
        super(new RubySquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Ruby Wall";
    }

    @Override
    public String getPluralName() {
        return "Ruby Walls";
    }

    @Override
    public String getDescription() {
        return "Ruby Walls are impassable without enough Ruby Squares.";
    }
}