/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericToggleWall;

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