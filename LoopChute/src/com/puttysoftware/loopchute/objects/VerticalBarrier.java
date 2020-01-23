/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericBarrier;

public class VerticalBarrier extends GenericBarrier {
    // Constructors
    public VerticalBarrier() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getBaseName() {
        return "vertical_barrier";
    }

    @Override
    public String getName() {
        return "Vertical Barrier";
    }

    @Override
    public String getPluralName() {
        return "Vertical Barriers";
    }

    @Override
    public String getDescription() {
        return "Vertical Barriers are impassable - you'll need to go around them.";
    }
}