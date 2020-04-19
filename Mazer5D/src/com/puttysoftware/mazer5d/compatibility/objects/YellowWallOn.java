/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class YellowWallOn extends GenericToggleWall {
    // Constructors
    public YellowWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall On";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls On";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls On can NOT be walked through, and will change to Yellow Walls Off when a Yellow Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.YELLOW_WALL_ON;
    }
}