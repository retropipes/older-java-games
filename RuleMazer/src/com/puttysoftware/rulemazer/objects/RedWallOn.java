/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericToggleWall;

public class RedWallOn extends GenericToggleWall {
    // Constructors
    public RedWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall On";
    }

    @Override
    public String getPluralName() {
        return "Red Walls On";
    }

    @Override
    public String getDescription() {
        return "Red Walls On can NOT be walked through, and will change to Red Walls Off when a Red Button is pressed.";
    }
}