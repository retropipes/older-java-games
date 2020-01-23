/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericToggleWall;

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
}