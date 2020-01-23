/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericToggleWall;

public class OrangeWallOn extends GenericToggleWall {
    // Constructors
    public OrangeWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall On";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls On";
    }

    @Override
    public String getDescription() {
        return "Orange Walls On can NOT be walked through, and will change to Orange Walls Off when a Orange Button is pressed.";
    }
}