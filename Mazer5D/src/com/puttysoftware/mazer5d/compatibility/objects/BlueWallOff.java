/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class BlueWallOff extends GenericToggleWall {
    // Constructors
    public BlueWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls Off";
    }

    @Override
    public String getDescription() {
        return "Blue Walls Off can be walked through, and will change to Blue Walls On when a Blue Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.BLUE_WALL_OFF;
    }
}