/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericToggleWall;

public class YellowWallOff extends GenericToggleWall {
    // Constructors
    public YellowWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls Off";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls Off can be walked through, and will change to Yellow Walls On when a Yellow Button is pressed.";
    }
}