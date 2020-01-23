/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericTeleport;
import com.puttysoftware.widgetwarren.generic.MazeObject;

public class Destination extends GenericTeleport {
    // Constructors
    public Destination() {
        super(0, 0, 0);
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
        return null;
    }

    @Override
    public String getDescription() {
        return "Destinations are where Teleports take you to.";
    }
}