/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class RoseWallOff extends GenericToggleWall {
    // Constructors
    public RoseWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Rose Walls Off";
    }

    @Override
    public String getDescription() {
        return "Rose Walls Off can be walked through, and will change to Rose Walls On when a Rose Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.ROSE_WALL_OFF;
    }
}