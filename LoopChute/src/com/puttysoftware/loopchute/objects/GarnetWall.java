/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class GarnetWall extends GenericCheckpoint {
    // Constructors
    public GarnetWall() {
        super(new GarnetSquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Garnet Wall";
    }

    @Override
    public String getPluralName() {
        return "Garnet Walls";
    }

    @Override
    public String getDescription() {
        return "Garnet Walls are impassable without enough Garnet Squares.";
    }
}