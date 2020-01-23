/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericGround;
import com.puttysoftware.brainmaze.generic.TypeConstants;

public class Tile extends GenericGround {
    // Constructors
    public Tile() {
        super(true, true, true, true, ColorConstants.COLOR_NONE);
    }

    @Override
    public final String getBaseName() {
        return "tile";
    }

    @Override
    public String getName() {
        return "Tile";
    }

    @Override
    public String getPluralName() {
        return "Tiles";
    }

    @Override
    public String getDescription() {
        return "Tile is one of the many types of ground - unlike other types of ground, objects can be pushed and pulled over Tiles.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}