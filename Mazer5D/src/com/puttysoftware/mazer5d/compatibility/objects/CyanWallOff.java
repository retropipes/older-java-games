/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class CyanWallOff extends GenericToggleWall {
    // Constructors
    public CyanWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Cyan Walls Off";
    }

    @Override
    public String getDescription() {
        return "Cyan Walls Off can be walked through, and will change to Cyan Walls On when a Cyan Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.CYAN_WALL_OFF;
    }
}