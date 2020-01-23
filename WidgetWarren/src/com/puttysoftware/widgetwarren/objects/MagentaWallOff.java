/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericToggleWall;

public class MagentaWallOff extends GenericToggleWall {
    // Constructors
    public MagentaWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls Off";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls Off can be walked through, and will change to Magenta Walls On when a Magenta Button is pressed.";
    }
}