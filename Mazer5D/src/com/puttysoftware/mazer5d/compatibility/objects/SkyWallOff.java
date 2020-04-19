/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SkyWallOff extends GenericToggleWall {
    // Constructors
    public SkyWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Sky Walls Off";
    }

    @Override
    public String getDescription() {
        return "Sky Walls Off can be walked through, and will change to Sky Walls On when a Sky Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SKY_WALL_OFF;
    }
}