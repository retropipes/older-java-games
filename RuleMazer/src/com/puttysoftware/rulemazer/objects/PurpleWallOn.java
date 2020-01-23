/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericToggleWall;

public class PurpleWallOn extends GenericToggleWall {
    // Constructors
    public PurpleWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Wall On";
    }

    @Override
    public String getPluralName() {
        return "Purple Walls On";
    }

    @Override
    public String getDescription() {
        return "Purple Walls On can NOT be walked through, and will change to Purple Walls Off when a Purple Button is pressed.";
    }
}