/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericToggleWall;

public class PurpleWallOff extends GenericToggleWall {
    // Constructors
    public PurpleWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Purple Walls Off";
    }

    @Override
    public String getDescription() {
        return "Purple Walls Off can be walked through, and will change to Purple Walls On when a Purple Button is pressed.";
    }
}