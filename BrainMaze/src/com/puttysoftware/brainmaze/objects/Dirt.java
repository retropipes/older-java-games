/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericGround;
import com.puttysoftware.brainmaze.generic.TypeConstants;

public class Dirt extends GenericGround {
    // Constructors
    public Dirt() {
        super(ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Dirt";
    }

    @Override
    public String getPluralName() {
        return "Squares of Dirt";
    }

    @Override
    public String getDescription() {
        return "Dirt is one of the many types of ground.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}