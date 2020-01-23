/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckpoint;

public class SapphireWall extends GenericCheckpoint {
    // Constructors
    public SapphireWall() {
        super(new SapphireSquare(), ColorConstants.COLOR_BROWN);
        this.setAttributeName("square");
        this.setAttributeTemplateColor(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getBaseName() {
        return "wall_on";
    }

    @Override
    public String getName() {
        return "Sapphire Wall";
    }

    @Override
    public String getPluralName() {
        return "Sapphire Walls";
    }

    @Override
    public String getDescription() {
        return "Sapphire Walls are impassable without enough Sapphire Squares.";
    }
}