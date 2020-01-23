/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;

public class GarnetSquare extends GenericCheckKey {
    // Constructors
    public GarnetSquare() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getBaseName() {
        return "square";
    }

    @Override
    public String getName() {
        return "Garnet Square";
    }

    @Override
    public String getPluralName() {
        return "Garnet Squares";
    }

    @Override
    public String getDescription() {
        return "Garnet Squares are the keys to Garnet Walls.";
    }
}