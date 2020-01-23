/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericToggleWall;

public class SkyWallOn extends GenericToggleWall {
    // Constructors
    public SkyWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Wall On";
    }

    @Override
    public String getPluralName() {
        return "Sky Walls On";
    }

    @Override
    public String getDescription() {
        return "Sky Walls On can NOT be walked through, and will change to Sky Walls Off when a Sky Button is pressed.";
    }
}