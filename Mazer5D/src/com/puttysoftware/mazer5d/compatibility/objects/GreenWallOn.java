/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class GreenWallOn extends GenericToggleWall {
    // Constructors
    public GreenWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall On";
    }

    @Override
    public String getPluralName() {
        return "Green Walls On";
    }

    @Override
    public String getDescription() {
        return "Green Walls On can NOT be walked through, and will change to Green Walls Off when a Green Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.GREEN_WALL_ON;
    }
}