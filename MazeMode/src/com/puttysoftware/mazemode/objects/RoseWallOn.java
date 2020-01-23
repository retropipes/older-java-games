/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericToggleWall;

public class RoseWallOn extends GenericToggleWall {
    // Constructors
    public RoseWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Wall On";
    }

    @Override
    public String getPluralName() {
        return "Rose Walls On";
    }

    @Override
    public String getDescription() {
        return "Rose Walls On can NOT be walked through, and will change to Rose Walls Off when a Rose Button is pressed.";
    }
}