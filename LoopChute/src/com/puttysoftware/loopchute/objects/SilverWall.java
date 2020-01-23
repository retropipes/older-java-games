/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class SilverWall extends GenericCheckpoint {
    // Constructors
    public SilverWall() {
        super(new SilverSquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Silver Wall";
    }

    @Override
    public String getPluralName() {
        return "Silver Walls";
    }

    @Override
    public String getDescription() {
        return "Silver Walls are impassable without enough Silver Squares.";
    }
}