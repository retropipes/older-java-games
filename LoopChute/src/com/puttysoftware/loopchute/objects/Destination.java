/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.GenericTeleport;
import com.puttysoftware.loopchute.generic.MazeObject;

public class Destination extends GenericTeleport {
    // Constructors
    public Destination() {
        super(0, 0, 0, false, "destination");
    }

    @Override
    public String getName() {
        return "Destination";
    }

    @Override
    public String getPluralName() {
        return "Destinations";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return this;
    }

    @Override
    public String getDescription() {
        return "";
    }
}