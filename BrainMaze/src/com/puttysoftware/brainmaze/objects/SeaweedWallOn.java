/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericToggleWall;

public class SeaweedWallOn extends GenericToggleWall {
    // Constructors
    public SeaweedWallOn() {
        super(true, ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall On";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls On";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls On can NOT be walked through, and will change to Seaweed Walls Off when a Seaweed Button is pressed.";
    }
}