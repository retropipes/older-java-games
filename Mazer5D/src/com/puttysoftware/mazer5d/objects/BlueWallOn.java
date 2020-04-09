/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericToggleWall;

public class BlueWallOn extends GenericToggleWall {
    // Constructors
    public BlueWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall On";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls On";
    }

    @Override
    public String getDescription() {
        return "Blue Walls On can NOT be walked through, and will change to Blue Walls Off when a Blue Button is pressed.";
    }
}