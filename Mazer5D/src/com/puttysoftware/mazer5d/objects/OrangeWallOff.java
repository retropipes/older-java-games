/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericToggleWall;

public class OrangeWallOff extends GenericToggleWall {
    // Constructors
    public OrangeWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls Off";
    }

    @Override
    public String getDescription() {
        return "Orange Walls Off can be walked through, and will change to Orange Walls On when a Orange Button is pressed.";
    }
}