/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class GoldenWall extends GenericCheckpoint {
    // Constructors
    public GoldenWall() {
        super(new GoldenSquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Golden Wall";
    }

    @Override
    public String getPluralName() {
        return "Golden Walls";
    }

    @Override
    public String getDescription() {
        return "Golden Walls are impassable without enough Golden Squares.";
    }
}