/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericToggleWall;

public class SeaweedWallOff extends GenericToggleWall {
    // Constructors
    public SeaweedWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls Off";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls Off can be walked through, and will change to Seaweed Walls On when a Seaweed Button is pressed.";
    }
}