/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericPassThroughObject;

public class Door extends GenericPassThroughObject {
    // Constructors
    public Door() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_DOOR);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public String getPluralName() {
        return "Doors";
    }

    @Override
    public String getDescription() {
        return "Doors are purely decorative.";
    }
}