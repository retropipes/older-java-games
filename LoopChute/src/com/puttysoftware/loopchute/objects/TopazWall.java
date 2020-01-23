/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class TopazWall extends GenericCheckpoint {
    // Constructors
    public TopazWall() {
        super(new TopazSquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Topaz Wall";
    }

    @Override
    public String getPluralName() {
        return "Topaz Walls";
    }

    @Override
    public String getDescription() {
        return "Topaz Walls are impassable without enough Topaz Squares.";
    }
}